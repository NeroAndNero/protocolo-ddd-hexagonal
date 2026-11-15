package com.neroandnero.cea.proyectos.infrastructure.controller;

public record CreateProjectRequest(
        String codeName,
        String commercialName,
        String startDate,
        String promoterDni
) {
}