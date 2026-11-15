package com.neroandnero.cea.proyectos.domain.valueobject;

import java.util.Objects;
import java.util.regex.Pattern;

public record EmployeeId(String value) {
    private static final Pattern FORMAT = Pattern.compile("^[A-Za-z0-9]{5,15}$");

    public EmployeeId {
        final String normalized = Objects.requireNonNull(value, "EmployeeId no puede ser nulo").trim();
        if (!FORMAT.matcher(normalized).matches()) {
            throw new IllegalArgumentException("El formato del EmployeeId (DNI) es inválido: " + normalized);
        }
        value = normalized;
    }
}