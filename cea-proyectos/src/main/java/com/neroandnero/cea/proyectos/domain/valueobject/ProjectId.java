package com.neroandnero.cea.proyectos.domain.valueobject;

public record ProjectId(String value) {
    public ProjectId {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("El ProjectId no puede estar vacío");
        }
        value = value.trim();
    }
}