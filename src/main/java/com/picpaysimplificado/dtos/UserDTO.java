package com.picpaysimplificado.dtos;

import java.math.BigDecimal;

public record UserDTO(String firtsName,
                      String lastName,
                      String email,
                      String document,
                      String userType,
                      BigDecimal balance,
                      String password) {
}
