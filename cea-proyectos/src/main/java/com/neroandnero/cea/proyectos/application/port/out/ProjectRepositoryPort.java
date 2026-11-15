package com.neroandnero.cea.proyectos.application.port.out;

import com.neroandnero.cea.proyectos.domain.ProjectStatus;
import com.neroandnero.cea.proyectos.domain.entity.ProjectModel;
import com.neroandnero.cea.proyectos.domain.valueobject.ProjectId;

import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryPort {
    ProjectModel save(ProjectModel project);
    Optional<ProjectModel> findById(ProjectId projectId);
    boolean existsById(ProjectId projectId);
    List<ProjectModel> findByStatus(ProjectStatus status);
}