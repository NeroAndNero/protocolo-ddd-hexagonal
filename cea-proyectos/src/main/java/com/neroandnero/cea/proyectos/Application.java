package com.neroandnero.cea.proyectos;

import com.neroandnero.cea.proyectos.application.dto.CreateProjectCommand;
import com.neroandnero.cea.proyectos.application.port.in.CreateProjectUseCase;
import com.neroandnero.cea.proyectos.application.port.out.ProjectRepositoryPort;
import com.neroandnero.cea.proyectos.application.service.CreateProjectService;
import com.neroandnero.cea.proyectos.domain.entity.ProjectModel;
import com.neroandnero.cea.proyectos.infrastructure.repository.InMemoryProjectRepository;

import java.time.LocalDate;

public class Application {
    public static void main(String[] args) {
        System.out.println("--- Iniciando CEA Proyectos (Arquitectura Hexagonal) ---");

        
        ProjectRepositoryPort repository = new InMemoryProjectRepository();
        CreateProjectUseCase createProjectService = new CreateProjectService(repository);

        
        CreateProjectCommand command = new CreateProjectCommand(
                "PROJ-001",
                "Sistema de Gestión de Empleados",
                LocalDate.of(2026, 5, 1),
                "12345678"
        );

        
        System.out.println("Creando proyecto...");
        ProjectModel savedProject = createProjectService.execute(command);

       
        System.out.println("¡Proyecto creado con éxito!");
        System.out.println("ID: " + savedProject.getId().value());
        System.out.println("Nombre: " + savedProject.getCommercialName().value());
        System.out.println("Estado: " + savedProject.getStatus());
        
    
        boolean exists = repository.existsById(savedProject.getId());
        System.out.println("¿Existe en la base de datos (memoria)? " + exists);
    }
}