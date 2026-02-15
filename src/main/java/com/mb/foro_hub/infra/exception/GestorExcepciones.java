package com.mb.foro_hub.infra.exception;

import com.mb.foro_hub.exceptions.ValidacionException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GestorExcepciones {

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity gestionarError404() {
            return ResponseEntity.notFound().build();
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity gestionarError400(HttpMessageNotReadableException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        @ExceptionHandler(ValidacionException.class)
        public ResponseEntity gestionarErrorDeValidacion(ValidacionException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity gestionarError500(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " +ex.getLocalizedMessage());
        }
}
