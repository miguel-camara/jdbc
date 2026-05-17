package com.miguel.repository;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import com.miguel.model.Category;

public class CategoryRepository implements Repository<Category> {
  private Connection conn;

  public CategoryRepository() {
  }

  public CategoryRepository(Connection conn) {
    this.conn = conn;
  }

  @Override
  public List<Category> list() throws SQLException {
    List<Category> categorys = new ArrayList<>();
    try (Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM categorias")) {
      while (rs.next()) {
        categorys.add(crearCategory(rs));
      }
    }
    return categorys;
  }

  public Connection getConn() {
    return conn;
  }

  public void setConn(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Category byId(Long id) throws SQLException {
    Category category = null;
    try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM categorias as c WHERE c.id=?")) {
      stmt.setLong(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          category = crearCategory(rs);
        }
      }
    }
    return category;
  }

  @Override
  public Category save(Category category) throws SQLException {
    String sql = null;
    if (category.getId() != null && category.getId() > 0) {
      sql = "UPDATE categorias SET categoria=? WHERE id=?";
    } else {
      sql = "INSERT INTO categorias(categoria) VALUES(?)";
    }
    try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, category.getName());
      if (category.getId() != null && category.getId() > 0) {
        stmt.setLong(2, category.getId());
      }
      stmt.executeUpdate();

      if (category.getId() == null) {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
          if (rs.next()) {
            category.setId(rs.getLong(1));
          }
        }
      }
    }
    return category;
  }

  @Override
  public void delete(Long id) throws SQLException {
    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM categorias WHERE id=?")) {
      stmt.setLong(1, id);
      stmt.executeUpdate();
    }
  }

  private Category crearCategory(ResultSet rs) throws SQLException {
    Category c = new Category();
    c.setId(rs.getLong("id"));
    c.setName(rs.getString("categoria"));
    return c;
  }
}
