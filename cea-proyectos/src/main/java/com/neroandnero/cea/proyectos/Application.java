package com.neroandnero.cea.proyectos;

import com.neroandnero.cea.proyectos.application.port.in.CreateProjectUseCase;
import com.neroandnero.cea.proyectos.application.port.out.ProjectRepositoryPort;
import com.neroandnero.cea.proyectos.application.service.CreateProjectService;
import com.neroandnero.cea.proyectos.domain.entity.ProjectModel;
import com.neroandnero.cea.proyectos.infrastructure.controller.CreateProjectRequest;
import com.neroandnero.cea.proyectos.infrastructure.controller.ProjectController;
import com.neroandnero.cea.proyectos.infrastructure.repository.InMemoryProjectRepository;

public class Application {
    public static void main(String[] args) {
        ProjectRepositoryPort repository = new InMemoryProjectRepository();
        CreateProjectUseCase createProjectService = new CreateProjectService(repository);
        
        ProjectController controller = new ProjectController(createProjectService);

        CreateProjectRequest request = new CreateProjectRequest(
                "PROJ-002",
                "Sistema de Nomina",
                "2026-06-01",
                "87654321"
        );

        ProjectModel savedProject = controller.handleCreateProject(request);

        System.out.println("--- Proyecto Creado ---");
        System.out.println("ID: " + savedProject.getId().value());
        System.out.println("Nombre: " + savedProject.getCommercialName().value());
        System.out.println("Estado: " + savedProject.getStatus());
    }
}