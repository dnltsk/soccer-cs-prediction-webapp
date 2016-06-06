package org.teeschke.soccer.cs.prediction.webapp;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by danielt on 28.02.16.
 */
public class SoccerDb {

  @Value("${db.host}")
  private String dbHost;

  @Value("${db.port}")
  private Integer dbPort;

  @Value("${db.db}")
  private String dbDb;

  @Value("${db.schema}")
  private String dbSchema;

  @Value("${db.user}")
  private String dbUser;

  @Value("${db.pass}")
  private String dbPass;

  public SoccerDb() {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public Connection getSoccerConnection() throws SQLException{
      return DriverManager.getConnection("jdbc:postgresql://"+dbHost+ ":"+dbPort+"/"+dbDb, dbUser, dbPass);
  }

}
