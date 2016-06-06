package org.teeschke.soccer.cs.prediction.webapp.prediction;

import org.teeschke.soccer.cs.prediction.webapp.Player;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

public class LinearModelTest {

  List<Player> teamA;
  List<Player> teamB;

  @BeforeClass
  public void createTeams(){
    Player a1 = new Player();
    a1.marketValueInMio=1.0f;
    teamA = Arrays.asList(a1);

    Player b1 = new Player();
    b1.marketValueInMio=2.0f;
    teamB = Arrays.asList(b1);
  }

  @Test
  public void r_script_is_executable() throws Exception {
    LinearModel linearModel = new LinearModel();
    PredictedResult predictedResult = linearModel.predictMatch(teamA, teamB);
    assertNotEquals(predictedResult, null);
  }
}