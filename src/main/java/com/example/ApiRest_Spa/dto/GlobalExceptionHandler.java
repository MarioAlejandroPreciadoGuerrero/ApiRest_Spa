package com.example.ApiRest_Spa.dto;



import com.example.ApiRest_Spa.exception.ResourceNotFoundException;
import com.example.ApiRest_Spa.exception.BusinessRulesException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> manejarNotFound(ResourceNotFoundException ex){
        ErrorDTO errorDTO = new ErrorDTO(
                "Recurso no encontrado",
                List.of(ex.getMessage()),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessRulesException.class)
    public ResponseEntity<ErrorDTO> manejarReglaDeNegocio(BusinessRulesException ex){
        ErrorDTO errorDTO = new ErrorDTO(
                "Solicitud inválida",
                List.of(ex.getMessage()),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
