package org.teeschke.soccer.cs.prediction.webapp;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.teeschke.soccer.cs.prediction.webapp.prediction.LinearModel;
import org.teeschke.soccer.cs.prediction.webapp.prediction.PredictedResult;
import org.teeschke.soccer.cs.prediction.webapp.team.TeamContainer;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends WebPage implements Serializable{
	private static final long serialVersionUID = 1L;

	private List<Player> teamA;
	private List<Player> teamB;
	private PredictedResult predictedResult;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		teamA = new ArrayList<>();
		teamB = new ArrayList<>();

		add(new TeamContainer("teamA", new PropertyModel<List<Player>>(HomePage.this, "teamA")));
		add(new TeamContainer("teamB", new PropertyModel<List<Player>>(HomePage.this, "teamB")));

		add(createPredictLink());

		createPredictedResultLabels();
	}

	private void createPredictedResultLabels() {
		add(new Label("goalsA", new Model(){
			@Override
			public Serializable getObject() {
				if(predictedResult!=null){
					return predictedResult.goalsA;
				}
				return "?";
			}
		}));
		add(new Label("goalsB", new Model(){
			@Override
			public Serializable getObject() {
				if(predictedResult!=null){
					return predictedResult.goalsB;
				}
				return "?";
			}
		}));
		add(new Label("goalsDiff", new Model(){
			@Override
			public Serializable getObject() {
				if(predictedResult!=null){
					return predictedResult.goalsDiff;
				}
				return "?";
			}
		}));
	}

	private Link createPredictLink() {
		return new Link("predictLink") {
			@Override
			public void onClick() {
				try {
					predictedResult = new LinearModel().predictMatch(teamA, teamB);
				} catch (IOException e) {
					e.printStackTrace();
				}
				setResponsePage(getPage());
			}
		};
	}
}
