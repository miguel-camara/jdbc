package com.miguel.services;

import java.sql.SQLException;
import java.util.List;

import com.miguel.model.*;

public interface Service {
  List<Product> list() throws SQLException;

  Product byId(Long id) throws SQLException;

  Product save(Product product) throws SQLException;

  void delete(Long id) throws SQLException;

  List<Category> categoryList() throws SQLException;

  Category categoryById(Long id) throws SQLException;

  Category categorySave(Category category) throws SQLException;

  void categoryDelete(Long id) throws SQLException;

  void saveProductWithCategory(Product product, Category category) throws SQLException;
}
