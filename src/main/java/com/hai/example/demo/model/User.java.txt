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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@JsonPropertyOrder({ "email", "name" })
@Entity // This tells Hibernate to make a table out of this class
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @NotNull(message = "Name cannot be null")
  private String name;

  @Size(min = 3, max = 18, message = "Sai email length")
  @Email(message = "Sai email format")
  private String email;

  // private ZoneId zone;
  // private ZonedDateTime dt;

  @Override
  public String toString() {
    return String.format("User[id=%d, Email='%s', Name='%s']", id, email, name);
  }
}