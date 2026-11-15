package com.neroandnero.cea.proyectos.infrastructure.controller;

import com.neroandnero.cea.proyectos.application.dto.CreateProjectCommand;
import com.neroandnero.cea.proyectos.application.port.in.CreateProjectUseCase;
import com.neroandnero.cea.proyectos.domain.entity.ProjectModel;

public class ProjectController {
    
    private final CreateProjectUseCase createProjectUseCase;
    private final ProjectEntrypointMapper mapper;

    public ProjectController(CreateProjectUseCase createProjectUseCase) {
        this.createProjectUseCase = createProjectUseCase;
        this.mapper = new ProjectEntrypointMapper();
    }

    public ProjectModel handleCreateProject(CreateProjectRequest request) {
        CreateProjectCommand command = mapper.toCommand(request);
        return createProjectUseCase.execute(command);
    }
}