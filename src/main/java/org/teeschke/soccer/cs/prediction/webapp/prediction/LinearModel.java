package org.teeschke.soccer.cs.prediction.webapp.prediction;

import org.teeschke.soccer.cs.prediction.webapp.Player;

import java.io.*;
import java.util.List;

public class LinearModel {

  public PredictedResult predictMatch(List<Player> teamA, List<Player> teamB) throws IOException {
    Float valueTeamA = calcTeamValue(teamA);
    Float valueTeamB = calcTeamValue(teamB);
    String output = executePredicitonScript(valueTeamA, valueTeamB);
    return parsePredictionResult(output);
  }

  private PredictedResult parsePredictionResult(String output) {
    String[] outputParts = output.split(" ");
    PredictedResult predictedResult = new PredictedResult();
    predictedResult.goalsA = Float.parseFloat(outputParts[0]);
    predictedResult.goalsB = Float.parseFloat(outputParts[1]);
    predictedResult.goalsDiff = Float.parseFloat(outputParts[2]);
    return predictedResult;
  }

  private String executePredicitonScript(Float valueTeamA, Float valueTeamB) throws IOException {
    Process p = Runtime.getRuntime().exec("Rscript src/main/resources/script/predict-lm.r "+valueTeamA +" "+valueTeamB);
    try(BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
      String line;
      while ((line = in.readLine()) != null) {
        return line;
      }
    }
    throw new RuntimeException("Missing output!");
  }

  private Float calcTeamValue(List<Player> team) {
    float sum = 0f;
    for(Player p : team){
      sum+=p.marketValueInMio;
    }
    return sum;
  }

}
