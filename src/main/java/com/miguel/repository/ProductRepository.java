package com.miguel.repository;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import com.miguel.model.Product;
import com.miguel.util.DatabaseConnection;

public class ProductRepository implements Repository<Product> {

  private Connection getConnection() throws SQLException {
    return DatabaseConnection.getInstance();
  }

  @Override
  public List<Product> list() {
    List<Product> products = new ArrayList<>();

    try (Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM productos")) {
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

    try (PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM productos WHERE id = ?")) {
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
      sql = "UPDATE productos SET nombre=?, precio=? WHERE id=?";
    } else {
      sql = "INSERT INTO productos(nombre, precio, fecha_creacion) VALUES(?,?,?)";
    }
    try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
      stmt.setString(1, product.getName());
      stmt.setLong(2, product.getPrice());

      if (product.getId() != null && product.getId() > 0) {
        stmt.setLong(3, product.getId());
      } else {
        stmt.setDate(3, new Date(product.getRegistrationDate().getTime()));
      }

      stmt.executeUpdate();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

  }

  @Override
  public void delete(Long id) {
    try (PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM productos WHERE id=?")) {
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
    return p;
  }
}
