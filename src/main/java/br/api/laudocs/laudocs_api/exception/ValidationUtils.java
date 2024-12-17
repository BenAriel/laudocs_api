package br.api.laudocs.laudocs_api.exception;

import java.time.LocalDate;

public class ValidationUtils {
    public static void checkVazio(String value, String exception) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(exception);
        }
    }

    public static void checkVazio(LocalDate dataConsulta, String exception) {
        if (dataConsulta == null || dataConsulta.toString().isEmpty()) {
            throw new ValidationException(exception);
        }
    }

    public static void checkVazio(Long pacienteId, String exception) {
        if (pacienteId == null || pacienteId.toString().isEmpty()) {
            throw new ValidationException(exception);
        }
    }
}
