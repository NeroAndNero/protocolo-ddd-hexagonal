package com.neroandnero.cea.proyectos.domain.valueobject;

import java.time.LocalDate;

public record TaskPeriod(LocalDate startDate, LocalDate endDate) {
    public TaskPeriod {
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("La fecha fin no puede ser anterior al inicio");
        }
    }
}