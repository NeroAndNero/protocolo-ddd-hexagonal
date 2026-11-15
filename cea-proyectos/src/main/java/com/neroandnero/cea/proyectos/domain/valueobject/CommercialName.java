package com.neroandnero.cea.proyectos.domain.valueobject;

public record CommercialName(String value) {
    public CommercialName {
        if (value == null || value.trim().length() < 3) {
            throw new IllegalArgumentException("El nombre comercial debe tener al menos 3 caracteres");
        }
        value = value.trim();
    }
}