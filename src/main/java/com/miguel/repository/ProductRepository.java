package com.miguel.repository;

import java.util.List;

import com.miguel.model.*;
import com.miguel.util.DatabaseConnection;
import java.sql.*;

import java.util.ArrayList;

public class ProductRepository implements Repository<Product> {

  private Connection getConnection() throws SQLException {
    return DatabaseConnection.getConnection();
  }

  @Override
  public List<Product> list() {
    List<Product> products = new ArrayList<>();
    try (Connection conn = getConnection();
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt
            .executeQuery("SELECT * FROM productos AS p INNER JOIN categorias AS c ON (p.categoria_id=c.id)")) {
      // ResultSet rs = stmt.executeQuery("SELECT * FROM productos")) {

      while (rs.next()) {
        Product p = crearProduct(rs);
        products.add(p);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return products;
  }

  @Override
  public Product byId(Long id) {
    Product product = null;

    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM productos AS p INNER JOIN categorias AS c ON (p.categoria_id=c.id) WHERE p.id = ?")) {
      // PreparedStatement stmt = conn.prepareStatement("SELECT * FROM productos WHERE
      // id = ?")) {
      stmt.setLong(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          product = crearProduct(rs);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return product;
  }

  @Override
  public void save(Product product) {
    String sql;
    if (product.getId() != null && product.getId() > 0) {
      sql = "UPDATE productos SET nombre=?, precio=?, categoria_id=? WHERE id=?";
    } else {
      sql = "INSERT INTO productos(nombre, precio, categoria_id, fecha_creacion) VALUES(?,?,?,?)";
    }
    // sql = "UPDATE productos SET nombre=?, precio=? WHERE id=?";
    // } else {
    // sql = "INSERT INTO productos(nombre, precio, fecha_creacion) VALUES(?,?,?)";
    // }
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, product.getName());
      stmt.setLong(2, product.getPrice());
      stmt.setLong(3, product.getCategory().getId());

      if (product.getId() != null && product.getId() > 0) {
        stmt.setLong(4, product.getId());
      } else {
        stmt.setDate(4, new Date(product.getRegistrationDate().getTime()));
      }

      stmt.executeUpdate();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

  }

  @Override
  public void delete(Long id) {
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM productos WHERE id=?")) {
      stmt.setLong(1, id);
      stmt.executeUpdate();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  private Product crearProduct(ResultSet rs) throws SQLException {
    Product p = new Product();
    p.setId(rs.getLong("id"));
    p.setName(rs.getString("nombre"));
    p.setPrice(rs.getInt("precio"));
    p.setRegistrationDate(rs.getDate("fecha_creacion"));
    Category categoria = new Category();
    categoria.setId(rs.getLong("categoria_id"));
    categoria.setName(rs.getString("categoria"));
    p.setCategory(categoria);
    return p;
  }
}
