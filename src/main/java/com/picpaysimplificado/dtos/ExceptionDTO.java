package com.picpaysimplificado.dtos;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionDTO(String message,
                           LocalDateTime timestamp,
                           HttpStatus status) {
}
