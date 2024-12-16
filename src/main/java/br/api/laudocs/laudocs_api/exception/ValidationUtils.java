package br.api.laudocs.laudocs_api.exception;

public class ValidationUtils {
    public static void checkVazio(String value, String exception) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(exception);
        }
    }
}
