package org.teeschke.soccer.cs.prediction.webapp.prediction;

import org.teeschke.soccer.cs.prediction.webapp.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class DecisionTreeModel implements PredicitonModel{

  @Override
  public PredictedResult predictMatch(List<Player> teamA, List<Player> teamB) throws IOException {
    String goalsA = executePredicitonScript(
        calcMidfieldMinusMidfield(teamA, teamB),
        calcMidfieldMinusDefense(teamA, teamB),
        calcOffenseGuestDefense(teamA, teamB),
        calcOffenseMinusKeeper(teamA, teamB),
        calcTeamMinusTeam(teamA, teamB));
    String goalsB = executePredicitonScript(
        calcMidfieldMinusMidfield(teamB, teamA),
        calcMidfieldMinusDefense(teamB, teamA),
        calcOffenseGuestDefense(teamB, teamA),
        calcOffenseMinusKeeper(teamB, teamA),
        calcTeamMinusTeam(teamB, teamA));
    return parsePredictionResult(goalsA, goalsB);
  }

  private PredictedResult parsePredictionResult(String goalsA, String goalsB) {
    PredictedResult predictedResult = new PredictedResult();
    predictedResult.goalsA = (float)Integer.parseInt(goalsA);
    predictedResult.goalsB = (float)Integer.parseInt(goalsB);
    predictedResult.goalsDiff = predictedResult.goalsA - predictedResult.goalsB;
    return predictedResult;
  }

  private Float calcTeamMinusTeam(List<Player> teamA, List<Player> teamB) {
    Float valueTeamA = calcTeamValue(teamA);
    Float valueTeamB = calcTeamValue(teamB);
    return valueTeamA - valueTeamB;
  }

  private Float calcOffenseGuestDefense(List<Player> teamA, List<Player> teamB) {
    Float offenseValueA = calcOffenseValue(teamA);
    Float defenseValueB = calcDefenseValue(teamB);
    return offenseValueA - defenseValueB;
  }

  private Float calcMidfieldMinusDefense(List<Player> teamA, List<Player> teamB) {
    Float midfieldValue = calcMidfieldValue(teamA);
    Float defenseValue = calcDefenseValue(teamB);
    return midfieldValue - defenseValue;
  }

  private Float calcMidfieldMinusMidfield(List<Player> teamA, List<Player> teamB) {
    Float midfieldValueA = calcMidfieldValue(teamA);
    Float midfieldValueB = calcMidfieldValue(teamB);
    return midfieldValueA - midfieldValueB;
  }

  private Float calcOffenseMinusKeeper(List<Player> teamA, List<Player> teamB) {
    Float offenseValue = calcOffenseValue(teamA);
    Float defenseValue = calcKeeperValue(teamB);
    return offenseValue - defenseValue;
  }

  private Float calcOffenseValue(List<Player> team) {
    return calcPositionValue(team, "OFF");
  }

  private Float calcMidfieldValue(List<Player> team) {
    return calcPositionValue(team, "MID");
  }

  private Float calcDefenseValue(List<Player> team) {
    return calcPositionValue(team, "DEF");
  }

  private Float calcKeeperValue(List<Player> team) {
    return calcPositionValue(team, "KEE");
  }

  private Float calcPositionValue(List<Player> team, String position) {
    float sum = 0f;
    for(Player p : team){
      if(p.verticalPosition.equals(position))
        sum+=p.marketValueInMio;
    }
    return sum;
  }

  private Float calcTeamValue(List<Player> team) {
    float sum = 0f;
    for(Player p : team){
      sum+=p.marketValueInMio;
    }
    return sum;
  }

  private String executePredicitonScript(Float hmidMinGmid, Float hmidMinGdef, Float hoffMinGdef, Float hoffMinKeep, Float teamMinTeam) throws IOException {
    Process p = Runtime.getRuntime().exec("Rscript src/main/resources/script/predict-dt.r "+hmidMinGmid +" "+hmidMinGdef+" "+hoffMinGdef+" "+hoffMinKeep+" "+teamMinTeam);
    try(BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
      String line;
      while ((line = in.readLine()) != null) {
        return line;
      }
    }
    throw new RuntimeException("Missing output!");
  }

}
