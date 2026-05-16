package com.miguel;

import java.sql.Connection;
import java.sql.SQLException;

import com.miguel.model.Product;
import com.miguel.repository.ProductRepository;
import com.miguel.repository.Repository;
import com.miguel.util.DatabaseConnection;
import java.util.Date;

public class Main {
  public static void main(String[] args) {
    dbExample();
    // dbUpdate();
    // dbDelete();
  }

  public static void dbExample() {
    try (Connection conn = DatabaseConnection.getInstance()) {

      Repository<Product> repositorio = new ProductRepository();
      System.out.println("============= listar =============");
      repositorio.list().forEach(System.out::println);

      System.out.println("============= obtener por id =============");
      System.out.println(repositorio.byId(1L));

      System.out.println("============= insertar nuevo producto =============");
      Product product = new Product();
      product.setName("Teclado mecánico");
      product.setPrice(500);
      product.setRegistrationDate(new Date());
      repositorio.save(product);
      System.out.println("Producto guardado con éxito");
      repositorio.list().forEach(System.out::println);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void dbUpdate() {
    try (Connection conn = DatabaseConnection.getInstance()) {

      Repository<Product> repositorio = new ProductRepository();
      System.out.println("============= listar =============");
      repositorio.list().forEach(System.out::println);

      System.out.println("============= obtener por id =============");
      System.out.println(repositorio.byId(1L));

      System.out.println("============= editar producto =============");
      Product product = new Product();
      product.setId(3L);
      product.setName("Teclado Razer mecánico");
      product.setPrice(700);
      repositorio.save(product);
      System.out.println("Producto editado con éxito");
      repositorio.list().forEach(System.out::println);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void dbDelete() {
    try (Connection conn = DatabaseConnection.getInstance()) {

      Repository<Product> repositorio = new ProductRepository();
      System.out.println("============= listar =============");
      repositorio.list().forEach(System.out::println);

      System.out.println("============= obtener por id =============");
      System.out.println(repositorio.byId(1L));

      System.out.println("============= Eliminar producto =============");
      repositorio.delete(3L);
      System.out.println("Producto eliminado con éxito");
      repositorio.list().forEach(System.out::println);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
