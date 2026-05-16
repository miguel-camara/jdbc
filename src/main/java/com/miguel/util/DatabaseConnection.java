package com.miguel.util;

import java.sql.*;

import org.apache.commons.dbcp2.BasicDataSource;

public class DatabaseConnection {
  private static String url = "jdbc:mysql://localhost:3306/java_curso?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrival=true";
  private static String username = "root";
  private static String password = "miguel98";
  private static BasicDataSource pool;

  public static BasicDataSource getInstance() throws SQLException {
    if (pool == null) {
      pool = new BasicDataSource();
      pool.setUrl(url);
      pool.setUsername(username);
      pool.setPassword(password);
      pool.setInitialSize(3);
      pool.setMinIdle(3);
      pool.setMaxIdle(8);
      pool.setMaxTotal(8);
    }

    return pool;
  }

  public static Connection getConnection() throws SQLException {
    return getInstance().getConnection();
  }
}
