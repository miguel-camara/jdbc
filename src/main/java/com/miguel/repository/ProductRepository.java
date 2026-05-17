package com.miguel.repository;

import java.util.List;

import com.miguel.model.*;
// import com.miguel.util.DatabaseConnection;
import java.sql.*;

import java.util.ArrayList;

public class ProductRepository implements Repository<Product> {

  // private Connection getConnection() throws SQLException {
  // return DatabaseConnection.getConnection();
  // }

  private Connection conn;

  public ProductRepository() {
  }

  public ProductRepository(Connection conn) {
    this.conn = conn;
  }

  @Override
  public List<Product> list() throws SQLException {
    List<Product> products = new ArrayList<>();
    // Connection conn = getConnection();
    try (Statement stmt = conn.createStatement();

        ResultSet rs = stmt
            .executeQuery("SELECT * FROM productos AS p INNER JOIN categorias AS c ON (p.categoria_id=c.id)")) {

      while (rs.next()) {
        Product p = crearProduct(rs);
        products.add(p);
      }
    }

    return products;
  }

  public void setConn(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Product byId(Long id) throws SQLException {
    Product product = null;

    // Connection conn = getConnection();
    try (
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
    }
    return product;
  }

  @Override
  public Product save(Product product) throws SQLException {
    String sql;
    if (product.getId() != null && product.getId() > 0) {
      sql = "UPDATE productos SET nombre=?, precio=?, categoria_id=?, sku=? WHERE id=?";
    } else {
      sql = "INSERT INTO productos(nombre, precio, categoria_id, sku, fecha_creacion) VALUES(?,?,?,?,?)";
    }
    // Connection conn = getConnection();
    try (
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, product.getName());
      stmt.setLong(2, product.getPrice());
      stmt.setLong(3, product.getCategory().getId());
      stmt.setString(4, product.getSku());

      if (product.getId() != null && product.getId() > 0) {
        stmt.setLong(5, product.getId());
      } else {
        stmt.setDate(5, new Date(product.getRegistrationDate().getTime()));
      }

      stmt.executeUpdate();

      if (product.getId() == null) {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
          if (rs.next()) {
            product.setId(rs.getLong(1));
          }
        }
      }

      return product;
    }

  }

  @Override
  public void delete(Long id) throws SQLException {
    // Connection conn = getConnection();
    try (
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM productos WHERE id=?")) {
      stmt.setLong(1, id);
      stmt.executeUpdate();
    }
  }

  private Product crearProduct(ResultSet rs) throws SQLException {
    Product p = new Product();
    p.setId(rs.getLong("id"));
    p.setName(rs.getString("nombre"));
    p.setPrice(rs.getInt("precio"));
    p.setRegistrationDate(rs.getDate("fecha_creacion"));
    p.setSku(rs.getString("sku"));
    Category categoria = new Category();
    categoria.setId(rs.getLong("categoria_id"));
    categoria.setName(rs.getString("categoria"));
    p.setCategory(categoria);
    return p;
  }

  public Connection getConn() {
    return conn;
  }
}
