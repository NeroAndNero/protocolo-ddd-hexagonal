package com.neroandnero.cea.proyectos.application.service;

import com.neroandnero.cea.proyectos.application.dto.CreateProjectCommand;
import com.neroandnero.cea.proyectos.application.port.in.CreateProjectUseCase;
import com.neroandnero.cea.proyectos.application.port.out.ProjectRepositoryPort;
import com.neroandnero.cea.proyectos.domain.entity.ProjectModel;
import com.neroandnero.cea.proyectos.domain.service.ProjectFactory;

public class CreateProjectService implements CreateProjectUseCase {

    private final ProjectRepositoryPort repository;

    
    public CreateProjectService(ProjectRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public ProjectModel execute(CreateProjectCommand command) {
        
        ProjectModel project = ProjectFactory.createNewProject(
                command.codeName(),
                command.commercialName(),
                command.startDate(),
                command.promoterDni()
        );

      
        return repository.save(project);
    }
}