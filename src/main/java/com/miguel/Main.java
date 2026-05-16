package com.miguel;

import java.sql.Connection;
import java.sql.SQLException;

import com.miguel.model.Product;
import com.miguel.repository.ProductRepository;
import com.miguel.repository.Repository;
import com.miguel.util.DatabaseConnection;
import java.util.Date;
import java.util.Scanner;

public class Main {
  static Scanner sc;
  static Connection conn;
  static Repository<Product> repositorio;

  public static void main(String[] args) {

    boolean isActive = true;

    try (Scanner s = new Scanner(System.in); Connection c = DatabaseConnection.getInstance()) {
      sc = s;
      conn = c;
      repositorio = new ProductRepository();

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
      } while (isActive);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // dbExample();
    // dbUpdate();
    // dbDelete();
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
    System.out.print("\n Id de producto: ");
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
    repositorio.save(product);
    System.out.println("\n ======== Producto guardado con éxito. ========\n");

  }

  public static void delete() throws SQLException {
    System.out.print("Id de producto a eliminar: ");
    Long id = sc.nextLong();

    if (repositorio.byId(id) != null) {
      repositorio.delete(id);
      System.out.println("\n\n======== Producto eliminado con éxito. ========\n");
    } else {
      System.out.printf("\nNo se encontro elemento con id = %d\n", id);
    }
  }

  public static void update() throws SQLException {
    System.out.print("Id producto a actualizar: ");
    Long id = sc.nextLong();
    Product product = repositorio.byId(id);

    if (product != null) {
      product.setName("Teclado mecánico " + (int) Math.floor(Math.random() * 1000 + 1));
      product.setPrice((int) Math.floor(Math.random() * 3000 + 1));
      repositorio.save(product);
      System.out.println("\n\n======== Producto Actualizadp con éxito. ========\n");
    } else {
      System.out.printf("\nNo se encontro elemento con id = %d\n", id);
    }

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
