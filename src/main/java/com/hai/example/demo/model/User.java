package com.hai.example.demo.model;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "email", "name" })
@Entity // This tells Hibernate to make a table out of this class
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @NotNull(message = "Name cannot be null")
  private String name;

  @Size(min = 3, max = 8, message = "Sai email length")
  @Email(message = "Sai email format")
  private String email;

  // private ZoneId zone;

  // private ZonedDateTime dt;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return "Ten: " + name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return String.format("User[id=%d, Email='%s', Name='%s']", id, email, name);
  }
}