package org.teeschke.soccer.cs.prediction.webapp.team;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.convert.IConverter;
import org.teeschke.soccer.cs.prediction.webapp.Player;
import org.teeschke.soccer.cs.prediction.webapp.PlayerConverter;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public abstract class TeamContainer extends Panel implements Serializable {

  @Inject
  PlayerRepository playerRepository;

  private PlayerConverter playerConverter = new PlayerConverter();

  private List<Player> team;

  private Player searchedPlayer;

  public TeamContainer(String id, final IModel<List<Player>> team) {
    super(id, new Model<Serializable>());
    this.team = team.getObject();

    Form f = new Form("linkForm");
    final AutoCompleteTextField<Player> autoCompleteTextField = createSearch();
    f.add(autoCompleteTextField);
    f.add(createAddLink(autoCompleteTextField));
    add(f);
    add(createTeamList());
  }

  abstract public void onTeamChanged();

  private ListView<Player> createTeamList() {
    return new ListView<Player>("playerList", new PropertyModel<List<Player>>(TeamContainer.this, "team")) {
      protected void populateItem(ListItem<Player> item) {
        item.add(new Label("player", playerConverter.convertToString(item.getModelObject(), Locale.GERMANY)));
        item.add(new Link<Player>("removePlayer", item.getModel()) {
          @Override
          public void onClick() {
            team.remove(getModelObject());
            onTeamChanged();
          }
        });
      }
    };
  }

  private SubmitLink createAddLink(final AutoCompleteTextField<Player> autoCompleteTextField) {
    return new SubmitLink("addLink") {
      public void onSubmit() {
        team.add(autoCompleteTextField.getModelObject());
        autoCompleteTextField.setModelObject(null);
        onTeamChanged();
      }
    };
  }

  private AutoCompleteTextField<Player> createSearch() {
    final AutoCompleteTextField<Player> autoCompleteTextField = new AutoCompleteTextField<Player>(
        "searchedPlayer",
        new PropertyModel<Player>(TeamContainer.this, "searchedPlayer"),
        new AbstractAutoCompleteRenderer<Player>() {
          @Override
          protected void renderChoice(Player object, Response response, String criteria) {
            response.write(playerConverter.convertToString(object, Locale.GERMANY));
          }

          @Override
          protected String getTextValue(Player object) {
            return playerConverter.convertToString(object, Locale.GERMANY);
          }
        }) {
      @Override
      protected Iterator<Player> getChoices(String searchTerm) {
        ArrayList<Player> players = playerRepository.loadPlayers(searchTerm);
        return players.iterator();
      }

      @Override
      public <Player> IConverter<Player> getConverter(Class<Player> type) {
        return (IConverter<Player>) new PlayerConverter();
      }

    };

    autoCompleteTextField.setOutputMarkupId(true);
    /*autoCompleteTextField.add(new AjaxFormComponentUpdatingBehavior("change") {
      @Override
      protected void onUpdate(AjaxRequestTarget target) {
        team.add(autoCompleteTextField.getModelObject());
        autoCompleteTextField.setModelObject(null);
        onTeamChanged();
        setResponsePage(getPage());
      }
    });*/
    return autoCompleteTextField;
  }

}