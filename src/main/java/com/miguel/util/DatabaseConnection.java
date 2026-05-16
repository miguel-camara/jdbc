package com.miguel.util;

import java.sql.*;

public class DatabaseConnection {
  private static String url = "jdbc:mysql://localhost:3306/java_curso?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrival=true";
  private static String username = "root";
  private static String password = "miguel98";
  private static Connection connection;

  public static Connection getInstance() throws SQLException {
    if (connection == null) {
      connection = DriverManager.getConnection(url, username, password);
    }
    return connection;
  }

}
