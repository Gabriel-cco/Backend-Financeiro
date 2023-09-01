package com.picpaysimplificado.infra;

import com.picpaysimplificado.dtos.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static java.lang.System.err;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler (value = {DataIntegrityViolationException.class})
    public ResponseEntity threatDuplicatedEntry(DataIntegrityViolationException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Usuário já cadastrado", LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDTO);
    }
    @ExceptionHandler (value = {EntityNotFoundException.class})
    public ResponseEntity treatNotFound(EntityNotFoundException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Usuário Não encontrado", LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler (value = {Exception.class})
    public ResponseEntity treatGeneralException(Exception e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionDTO);
    }
}
