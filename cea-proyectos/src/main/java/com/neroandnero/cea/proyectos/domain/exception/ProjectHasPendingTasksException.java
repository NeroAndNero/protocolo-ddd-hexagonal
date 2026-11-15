package com.neroandnero.cea.proyectos.domain.exception;

public class ProjectHasPendingTasksException extends DomainException {
    public ProjectHasPendingTasksException(String message) {
        super(message);
    }
    
    public static ProjectHasPendingTasksException becauseTasksAreNotDone(String projectId, long pending) {
        return new ProjectHasPendingTasksException("El proyecto " + projectId + " no puede finalizar. Tiene " + pending + " tareas pendientes.");
    }
}