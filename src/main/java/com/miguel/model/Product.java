package com.miguel.model;

import java.util.Date;

public class Product {
  private Long id;
  private String name;
  private Integer price;
  private Date registrationDate;
  private Category category;
  private String sku;

  public Product() {
  }

  public Product(Long id, String name, Integer price, Date registrationDate) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.registrationDate = registrationDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public Date getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(Date registrationDate) {
    this.registrationDate = registrationDate;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  @Override
  public String toString() {
    return "Product [id=" + id + ", name=" + name + ", price=" + price + ", registrationDate=" + registrationDate
        + ", sku=" + sku + ", category=" + category + "]";
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

}
