package com.neroandnero.cea.proyectos.application.service;

import com.neroandnero.cea.proyectos.application.dto.CreateProjectCommand;
import com.neroandnero.cea.proyectos.application.port.out.ProjectRepositoryPort;
import com.neroandnero.cea.proyectos.domain.ProjectStatus;
import com.neroandnero.cea.proyectos.domain.entity.ProjectModel;
import com.neroandnero.cea.proyectos.domain.valueobject.ProjectId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CreateProjectServiceTest {

    @Test
    void deberiaCrearYGuardarProyecto() {
        ProjectRepositoryPort fakeRepository = new FakeRepository();
        CreateProjectService service = new CreateProjectService(fakeRepository);

        CreateProjectCommand command = new CreateProjectCommand(
                "TEST-01", "Proyecto Test", LocalDate.now(), "12345678"
        );

        ProjectModel result = service.execute(command);

        assertNotNull(result);
        assertEquals(ProjectStatus.PLANNED, result.getStatus());
        assertTrue(fakeRepository.existsById(result.getId()));
    }

    static class FakeRepository implements ProjectRepositoryPort {
        private final Map<String, ProjectModel> db = new HashMap<>();

        @Override
        public ProjectModel save(ProjectModel project) {
            db.put(project.getId().value(), project);
            return project;
        }

        @Override
        public Optional<ProjectModel> findById(ProjectId projectId) {
            return Optional.ofNullable(db.get(projectId.value()));
        }

        @Override
        public boolean existsById(ProjectId projectId) {
            return db.containsKey(projectId.value());
        }

        @Override
        public List<ProjectModel> findByStatus(ProjectStatus status) {
            return List.of();
        }
    }
}