package com.neroandnero.cea.proyectos.application.dto;

import java.time.LocalDate;

public record CreateProjectCommand(
        String codeName,
        String commercialName,
        LocalDate startDate,
        String promoterDni
) {
}