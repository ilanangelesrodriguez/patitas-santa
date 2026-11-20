package org.uns.patitassanta.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ReglaNegocioException.class)
    ResponseEntity<ApiError> regla(ReglaNegocioException e) {
        return error(e.getStatus(), e.getMessage(), Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> validacion(MethodArgumentNotValidException e) {
        Map<String, String> campos = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach(f ->
                campos.putIfAbsent(f.getField(), f.getDefaultMessage()));
        return error(HttpStatus.BAD_REQUEST, "Datos de entrada inválidos", campos);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ApiError> json(HttpMessageNotReadableException e) {
        return error(HttpStatus.BAD_REQUEST, "JSON incorrecto o valor no reconocido", Map.of());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<ApiError> integridad(DataIntegrityViolationException e) {
        return error(HttpStatus.CONFLICT, "Conflicto de datos: restricción de base de datos", Map.of());
    }

    private ResponseEntity<ApiError> error(HttpStatus status, String mensaje, Map<String, String> errores) {
        return ResponseEntity.status(status)
                .body(new ApiError(LocalDateTime.now(), status.value(), mensaje, errores));
    }
}
