package com.neroandnero.cea.proyectos.domain.valueobject;

public record TaskDuration(int days) {
    public TaskDuration {
        if (days < 1) {
            throw new IllegalArgumentException("La duración de la tarea debe ser de al menos 1 día");
        }
    }
}