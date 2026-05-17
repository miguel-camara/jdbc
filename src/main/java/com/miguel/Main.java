package com.miguel;

import com.miguel.model.*;

import com.miguel.repository.*;

import com.miguel.util.DatabaseConnection;
import com.miguel.services.CatalogService;
import com.miguel.services.Service;

import java.sql.*;

import java.util.Date;
import java.util.Scanner;

public class Main {
  static Scanner sc;
  static Repository<Product> repositorio;
  static Connection conn;

  public static void main(String[] args) throws SQLException {

    // dbExample();
    // dbUpdate();
    // dbDelete();
    // dbService();
    init();
  }

  public static void init() {
    try (Connection c = DatabaseConnection.getConnection(); Scanner s = new Scanner(System.in)) {
      boolean isActive = true;
      sc = s;
      conn = c;
      repositorio = new ProductRepository(c);
      if (conn.getAutoCommit()) {
        conn.setAutoCommit(false);
      }

      try {
        do {
          switch (menu()) {
            case 1:
              list();
              break;
            case 2:
              byId();
              break;
            case 3:
              add();
              break;
            case 4:
              update();
              break;
            case 5:
              delete();
              break;

            default:
              isActive = false;
              break;
          }
          conn.commit();
        } while (isActive);
      } catch (SQLException e) {
        conn.rollback();
        e.printStackTrace();
      }

    } catch (Exception e) {
      System.err.println("Exception Error");
    }
  }

  public static byte menu() {
    System.out.println("\n===========================");
    System.out.println("\t1. Listar.");
    System.out.println("\t2. Por ID.");
    System.out.println("\t3. Agregar.");
    System.out.println("\t4. Actualizar.");
    System.out.println("\t5. Eliminar.");
    System.out.println("\t6. Salir.");
    byte op = sc.nextByte();
    System.out.println("===========================\n");
    // sc.close();
    return op;
  }

  public static void list() throws SQLException {
    System.out.println("\n ============= listar ============= \n");
    repositorio.list().forEach(System.out::println);

  }

  public static void byId() throws SQLException {
    System.out.print("\n Id de product: ");
    Long id = sc.nextLong();

    Product p = repositorio.byId(id);

    if (p != null) {
      System.out.printf("\n%s\n", p);
    } else {
      System.out.printf("\nNo se encontro elemento con id = %d\n", id);
    }

  }

  public static void add() throws SQLException {
    Product product = new Product();
    product.setName("Teclado mecánico " + (int) Math.floor(Math.random() * 1000 + 1));
    product.setPrice((int) Math.floor(Math.random() * 3000 + 1));
    product.setRegistrationDate(new Date());
    product.setSku(Math.floor(Math.random() * 10000 + 1) + "");
    // product.setSku("asdfgh");
    Category category = new Category();
    category.setId(2L);
    product.setCategory(category);
    repositorio.save(product);
    System.out.println("\n ======== Product guardado con éxito. ========\n");

  }

  public static void delete() throws SQLException {
    System.out.print("Id de product a eliminar: ");
    Long id = sc.nextLong();

    if (repositorio.byId(id) != null) {
      repositorio.delete(id);
      System.out.println("\n\n======== Product eliminado con éxito. ========\n");
    } else {
      System.out.printf("\nNo se encontro elemento con id = %d\n", id);
    }
  }

  public static void update() throws SQLException {
    System.out.print("Id product a actualizar: ");
    Long id = sc.nextLong();
    Product product = repositorio.byId(id);

    if (product != null) {
      product.setName("Teclado mecánico " + (int) Math.floor(Math.random() * 1000 + 1));
      product.setPrice((int) Math.floor(Math.random() * 3000 + 1));
      repositorio.save(product);
      System.out.println("\n\n======== Product Actualizadp con éxito. ========\n");
    } else {
      System.out.printf("\nNo se encontro elemento con id = %d\n", id);
    }

  }

  public static void dbExample() throws SQLException {

    System.out.println("============= listar =============");
    repositorio.list().forEach(System.out::println);

    System.out.println("============= obtener por id =============");
    System.out.println(repositorio.byId(1L));

    System.out.println("============= insertar nuevo product =============");
    Product product = new Product();
    product.setName("Teclado mecánico");
    product.setPrice(500);
    product.setRegistrationDate(new Date());
    repositorio.save(product);
    System.out.println("Product guardado con éxito");
    repositorio.list().forEach(System.out::println);

  }

  public static void dbUpdate() throws SQLException {

    System.out.println("============= listar =============");
    repositorio.list().forEach(System.out::println);

    System.out.println("============= obtener por id =============");
    System.out.println(repositorio.byId(1L));

    System.out.println("============= editar product =============");
    Product product = new Product();
    product.setId(3L);
    product.setName("Teclado Razer mecánico");
    product.setPrice(700);
    repositorio.save(product);
    System.out.println("Product editado con éxito");
    repositorio.list().forEach(System.out::println);

  }

  public static void dbDelete() throws SQLException {

    System.out.println("============= listar =============");
    repositorio.list().forEach(System.out::println);

    System.out.println("============= obtener por id =============");
    System.out.println(repositorio.byId(1L));

    System.out.println("============= Eliminar product =============");
    repositorio.delete(3L);
    System.out.println("Product eliminado con éxito");
    repositorio.list().forEach(System.out::println);

  }

  public static void dbService() throws SQLException {
    Service service = new CatalogService();
    System.out.println("============= listar =============");
    service.list().forEach(System.out::println);
    Category category = new Category();
    category.setName("Iluminación");

    Product product = new Product();
    product.setName("Lámpara led escritorio");
    product.setPrice(990);
    product.setRegistrationDate(new Date());
    product.setSku("abcdefgh12");
    service.saveProductWithCategory(product, category);
    System.out.println("Product guardado con éxito: " + product.getId());
    service.list().forEach(System.out::println);
  }

}
