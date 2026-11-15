package com.neroandnero.cea.proyectos.domain.valueobject;

import java.time.LocalDate;

public record ProjectDateRange(LocalDate startDate, LocalDate endDate) {
    
    // Constructor con validación (invariante)
    public ProjectDateRange {
        if (startDate == null) {
            throw new IllegalArgumentException("La fecha de inicio es obligatoria");
        }
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
    }
    
    // Método para actualizar la fecha de fin manteniendo la de inicio
    public ProjectDateRange withEndDate(LocalDate newEndDate) {
        return new ProjectDateRange(this.startDate, newEndDate);
    }
}