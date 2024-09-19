package postech.fiap.com.br.reservas_avaliacoes_api.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<List<String>> handleValidationException(
                                                MethodArgumentNotValidException ex) {
        List<String> errors =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> error.getField()
                                + ": " + error.getDefaultMessage())
                        .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(
            ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        if (ex.getMessage().contains("The given id must not be null")) {
            return ResponseEntity.badRequest().body("O ID do recurso é inválido. Por favor, verifique o ID e tente novamente.");
        } else {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Verifique se todos os campos enviados estão corretos.");
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(
            NullPointerException ex) {
        return ResponseEntity.badRequest().body("Verifique se todos os campos estão sendo enviados, verifique se existe algum campo Null");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("Método HTTP não suportado.");
    }


    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public ResponseEntity<Object> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException ex) {

        String bodyOfResponse = "Formato de dados inválido. Por favor, envie dados no formato JSON.";
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }



}
