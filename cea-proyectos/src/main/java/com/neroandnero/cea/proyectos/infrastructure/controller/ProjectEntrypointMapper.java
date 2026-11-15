package com.neroandnero.cea.proyectos.infrastructure.controller;

import com.neroandnero.cea.proyectos.application.dto.CreateProjectCommand;
import java.time.LocalDate;

public class ProjectEntrypointMapper {
    
    public CreateProjectCommand toCommand(CreateProjectRequest request) {
        return new CreateProjectCommand(
                request.codeName(),
                request.commercialName(),
                LocalDate.parse(request.startDate()),
                request.promoterDni()
        );
    }
}