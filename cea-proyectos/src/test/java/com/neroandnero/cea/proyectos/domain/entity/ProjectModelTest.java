package com.neroandnero.cea.proyectos.domain.entity;

import com.neroandnero.cea.proyectos.domain.exception.ProjectHasPendingTasksException;
import com.neroandnero.cea.proyectos.domain.valueobject.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ProjectModelTest {

    @Test
    void noDeberiaFinalizarConTareasPendientes() {
        // 1. Preparamos un proyecto válido
        ProjectModel proyecto = new ProjectModel(
                new ProjectId("PROJ-01"),
                new CommercialName("Sistema de Ventas"),
                new ProjectDateRange(LocalDate.of(2026, 1, 1), null),
                new EmployeeId("12345678")
        );

        // 2. Le agregamos una tarea (que por defecto estará en TODO)
        proyecto.addTask(new TaskModel("Diseñar base de datos", new TaskDuration(5)));

        // 3. Intentamos finalizar el proyecto y verificamos que lance el error
        assertThrows(ProjectHasPendingTasksException.class, () -> {
            proyecto.finish(LocalDate.of(2026, 6, 1));
        });

        // 4. Verificamos que el estado NO cambió (sigue PLANIFICADO)
        assertEquals(com.neroandnero.cea.proyectos.domain.ProjectStatus.PLANNED, proyecto.getStatus());
    }
}