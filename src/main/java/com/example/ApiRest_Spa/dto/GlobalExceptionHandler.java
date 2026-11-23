package com.example.ApiRest_Spa.dto;

import com.example.ApiRest_Spa.exception.ResourceNotFoundException;
import com.example.ApiRest_Spa.exception.UnauthorizedException;
import com.example.ApiRest_Spa.exception.BusinessRulesException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorDTO> manejarNotFound(ResourceNotFoundException ex) {
                ErrorDTO errorDTO = new ErrorDTO(
                                "Recurso no encontrado",
                                List.of(ex.getMessage()),
                                LocalDateTime.now());
                return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(BusinessRulesException.class)
        public ResponseEntity<ErrorDTO> manejarReglaDeNegocio(BusinessRulesException ex) {
                ErrorDTO errorDTO = new ErrorDTO(
                                "Solicitud inválida",
                                List.of(ex.getMessage()),
                                LocalDateTime.now());
                return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorDTO> manejarValidacion(MethodArgumentNotValidException ex) {
                List<String> detalles = ex.getBindingResult().getFieldErrors().stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .toList();
                ErrorDTO errorDTO = new ErrorDTO(
                                "Datos inválidos",
                                detalles,
                                LocalDateTime.now());
                return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ErrorDTO> manejarJsonInvalido(HttpMessageNotReadableException ex) {
                ErrorDTO errorDTO = new ErrorDTO(
                                "JSON inválido",
                                List.of("Revisa el formato del cuerpo de la petición"),
                                LocalDateTime.now());
                return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(IllegalStateException.class)
        public ResponseEntity<ErrorDTO> manejarEstadoInvalido(IllegalStateException ex) {
                ErrorDTO errorDTO = new ErrorDTO(
                                "Solicitud inválida",
                                List.of(ex.getMessage()),
                                LocalDateTime.now());
                return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ErrorDTO> manejarConstraintViolation(ConstraintViolationException ex) {
                List<String> detalles = ex.getConstraintViolations().stream()
                                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                                .toList();
                ErrorDTO errorDTO = new ErrorDTO(
                                "Datos inválidos",
                                detalles,
                                LocalDateTime.now());
                return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(UnauthorizedException.class)
        public ResponseEntity<ErrorDTO> manejarNoAutorizado(UnauthorizedException ex) {
                ErrorDTO errorDTO = new ErrorDTO(
                                "No autorizado",
                                List.of(ex.getMessage()),
                                LocalDateTime.now());
                return new ResponseEntity<>(errorDTO, HttpStatus.UNAUTHORIZED);
        }
}
