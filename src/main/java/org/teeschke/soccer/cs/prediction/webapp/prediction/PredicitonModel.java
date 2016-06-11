package org.teeschke.soccer.cs.prediction.webapp.prediction;

import org.teeschke.soccer.cs.prediction.webapp.Player;

import java.util.List;

public interface PredicitonModel {

  public PredictedResult predictMatch(List<Player> teamA, List<Player> teamB) throws Exception;

}
