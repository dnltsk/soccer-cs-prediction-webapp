package org.teeschke.soccer.cs.prediction.webapp.team;

import org.springframework.stereotype.Service;
import org.teeschke.soccer.cs.prediction.webapp.Player;
import org.teeschke.soccer.cs.prediction.webapp.SoccerDb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@Service
public class PlayerRepository extends SoccerDb {

  public ArrayList<Player> loadPlayers(String searchTerm){
    StringBuilder sql = new StringBuilder();
    sql.append("  select ");
    sql.append("     id, full_name, nationality, position_vertical, ");
    sql.append("     (select value_in_mio from player_market_value v where v.id_player = p.id order by date desc limit 1) as current_value_in_mio");
    sql.append("  from ");
    sql.append("    player p ");
    sql.append("  where ");
    sql.append("    lower(full_name) like '%"+searchTerm.toLowerCase()+"%' ");
    sql.append("  order by ");
    sql.append("    full_name ");
    sql.append("  limit ");
    sql.append("    10 ");
    System.out.println(sql.toString());

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {

      conn = getSoccerConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql.toString());

      ArrayList players = new ArrayList();
      while(rs.next()){
        Player player = new Player();
        player.id = rs.getLong("id");
        player.fullName = rs.getString("full_name");
        player.nationality = rs.getString("nationality");
        player.verticalPosition = rs.getString("position_vertical");
        player.marketValueInMio = rs.getFloat("current_value_in_mio");
        players.add(player);
      }
      return players;
    } catch (SQLException e) {
      System.err.println("error while select "+sql.toString());
      System.err.println(e.getLocalizedMessage());
    } finally {
      closeStuff(conn, stmt, rs);
    }
    return new ArrayList<>();
  }

  private static void closeStuff(Connection conn, Statement stmt, ResultSet rs) {
    try {
      if(conn != null && !conn.isClosed()){
        conn.close();
      }
      if(stmt != null && !stmt.isClosed()){
        stmt.close();
      }
      if(rs != null &&  !rs.isClosed()){
        rs.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
