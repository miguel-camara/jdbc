package com.miguel.services;

import java.sql.*;
import java.util.List;

import com.miguel.model.*;
import com.miguel.repository.*;
import com.miguel.util.DatabaseConnection;

public class CatalogService implements Service {
  private Repository<Product> productRepository;
  private Repository<Category> categoryRepository;

  public CatalogService() {
    this.productRepository = new ProductRepository();
    this.categoryRepository = new CategoryRepository();
  }

  @Override
  public List<Product> list() throws SQLException {
    try (Connection conn = DatabaseConnection.getConnection()) {

      productRepository.setConn(conn);
      return productRepository.list();
    }

  }

  @Override
  public Product byId(Long id) throws SQLException {
    try (Connection conn = DatabaseConnection.getConnection()) {
      productRepository.setConn(conn);
      return productRepository.byId(id);
    }
  }

  @Override
  public Product save(Product product) throws SQLException {
    try (Connection conn = DatabaseConnection.getConnection()) {
      productRepository.setConn(conn);
      if (conn.getAutoCommit()) {
        conn.setAutoCommit(false);
      }
      Product newProduct = null;
      try {
        newProduct = productRepository.save(product);
        conn.commit();
      } catch (SQLException e) {
        conn.rollback();
        e.printStackTrace();
      }
      return newProduct;
    }
  }

  @Override
  public void delete(Long id) throws SQLException {
    try (Connection conn = DatabaseConnection.getConnection()) {
      productRepository.setConn(conn);
      if (conn.getAutoCommit()) {
        conn.setAutoCommit(false);
      }
      try {
        productRepository.delete(id);
        conn.commit();
      } catch (SQLException e) {
        conn.rollback();
        e.printStackTrace();
      }
    }
  }

  @Override
  public List<Category> categoryList() throws SQLException {
    try (Connection conn = DatabaseConnection.getConnection()) {
      categoryRepository.setConn(conn);
      return categoryRepository.list();
    }
  }

  @Override
  public Category categoryById(Long id) throws SQLException {
    try (Connection conn = DatabaseConnection.getConnection()) {
      categoryRepository.setConn(conn);
      return categoryRepository.byId(id);
    }
  }

  @Override
  public Category categorySave(Category category) throws SQLException {
    try (Connection conn = DatabaseConnection.getConnection()) {
      categoryRepository.setConn(conn);

      if (conn.getAutoCommit()) {
        conn.setAutoCommit(false);
      }
      Category newCategory = null;
      try {
        newCategory = categoryRepository.save(category);
        conn.commit();
      } catch (SQLException e) {
        conn.rollback();
        e.printStackTrace();
      }
      return newCategory;
    }
  }

  @Override
  public void categoryDelete(Long id) throws SQLException {
    try (Connection conn = DatabaseConnection.getConnection()) {
      categoryRepository.setConn(conn);

      if (conn.getAutoCommit()) {
        conn.setAutoCommit(false);
      }
      try {
        categoryRepository.delete(id);
        conn.commit();
      } catch (SQLException e) {
        conn.rollback();
        e.printStackTrace();
      }
    }

  }

  @Override
  public void saveProductWithCategory(Product product, Category category) throws SQLException {
    try (Connection conn = DatabaseConnection.getConnection()) {
      productRepository.setConn(conn);
      categoryRepository.setConn(conn);

      if (conn.getAutoCommit()) {
        conn.setAutoCommit(false);
      }
      try {
        Category newCategory = categoryRepository.save(category);
        product.setCategory(newCategory);
        productRepository.save(product);
        conn.commit();
      } catch (SQLException e) {
        conn.rollback();
        e.printStackTrace();
      }
    }

  }
}
