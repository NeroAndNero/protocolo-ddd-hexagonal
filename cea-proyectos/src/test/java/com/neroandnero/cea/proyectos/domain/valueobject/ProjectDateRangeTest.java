package com.neroandnero.cea.proyectos.domain.valueobject;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ProjectDateRangeTest {

    @Test
    void deberiaRechazarFechaFinAnteriorAlInicio() {
        LocalDate inicio = LocalDate.of(2026, 2, 1);
        LocalDate fin = LocalDate.of(2026, 1, 1); // Fecha anterior

        // Verificamos que lance la excepción
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectDateRange(inicio, fin);
        });
    }
}