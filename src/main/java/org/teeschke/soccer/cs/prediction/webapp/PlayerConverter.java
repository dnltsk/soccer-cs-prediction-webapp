package org.teeschke.soccer.cs.prediction.webapp;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import java.util.Locale;

public class PlayerConverter implements IConverter<Player> {

  private String delim = ", ";

  @Override
  public Player convertToObject(String s, Locale locale) throws ConversionException {
    try {
      String[] text = s.split(delim);
      Player player = new Player();
      player.id = Long.parseLong(text[0]);
      player.nationality = text[1];
      player.fullName = text[2];
      player.verticalPosition = text[3];
      player.marketValueInMio = Float.parseFloat(text[4]);
      return player;
    } catch (NumberFormatException e) {
      return null;
    }
  }

  @Override
  public String convertToString(Player player, Locale locale) {
    if(player == null){
      return "";
    }
    return player.id+delim+
        player.nationality+delim+
        player.fullName+delim+
        player.verticalPosition+delim+
        player.marketValueInMio;
  }
}
