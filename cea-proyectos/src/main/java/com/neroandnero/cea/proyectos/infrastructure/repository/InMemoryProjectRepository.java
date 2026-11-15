package com.neroandnero.cea.proyectos.infrastructure.repository;

import com.neroandnero.cea.proyectos.application.port.out.ProjectRepositoryPort;
import com.neroandnero.cea.proyectos.domain.ProjectStatus;
import com.neroandnero.cea.proyectos.domain.entity.ProjectModel;
import com.neroandnero.cea.proyectos.domain.valueobject.ProjectId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryProjectRepository implements ProjectRepositoryPort {
    
    
    private final Map<String, ProjectModel> database = new HashMap<>();

    @Override
    public ProjectModel save(ProjectModel project) {
        database.put(project.getId().value(), project);
        return project;
    }

    @Override
    public Optional<ProjectModel> findById(ProjectId projectId) {
        return Optional.ofNullable(database.get(projectId.value()));
    }

    @Override
    public boolean existsById(ProjectId projectId) {
        return database.containsKey(projectId.value());
    }

    @Override
    public List<ProjectModel> findByStatus(ProjectStatus status) {
        return database.values().stream()
                .filter(p -> p.getStatus() == status)
                .collect(Collectors.toList());
    }
}