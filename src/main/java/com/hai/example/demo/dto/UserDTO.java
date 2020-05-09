package com.hai.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserDTO implements Serializable {
    private BigDecimal userId;
    private String fullName;
    private String emailAddress;

    public String getFullName() {
        return String.format("Ten: %s", this.fullName);
    }
}
