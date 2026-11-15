package com.neroandnero.cea.proyectos.application.port.in;

import com.neroandnero.cea.proyectos.application.dto.CreateProjectCommand;
import com.neroandnero.cea.proyectos.domain.entity.ProjectModel;

public interface CreateProjectUseCase {
    ProjectModel execute(CreateProjectCommand command);
}