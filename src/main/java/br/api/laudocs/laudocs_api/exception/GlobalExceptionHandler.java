package br.api.laudocs.laudocs_api.exception;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                                  .getAllErrors()
                                  .stream()
                                  .map(error -> error.getDefaultMessage())
                                  .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException ex) {
        return ResponseEntity.badRequest().body(
            Map.of(
                "message", ex.getMensagem(),
                "error", "Erro nos parâmetros."
            )
        );
    }
}
