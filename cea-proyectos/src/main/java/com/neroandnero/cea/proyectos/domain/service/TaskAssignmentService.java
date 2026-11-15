package com.neroandnero.cea.proyectos.domain.service;

import com.neroandnero.cea.proyectos.domain.entity.EmployeeModel;
import com.neroandnero.cea.proyectos.domain.policy.AssignmentConflictPolicy;
import com.neroandnero.cea.proyectos.domain.valueobject.TaskPeriod;

import java.util.List;

public class TaskAssignmentService {
    
    private final AssignmentConflictPolicy conflictPolicy;

    public TaskAssignmentService(AssignmentConflictPolicy conflictPolicy) {
        this.conflictPolicy = conflictPolicy;
    }

    public void validateAssignment(EmployeeModel employee, List<TaskPeriod> existingCommitments) {
        if (!employee.isActive()) {
            throw new IllegalArgumentException("El empleado no está activo y no puede ser asignado.");
        }
        
        if (!conflictPolicy.canAssign(existingCommitments)) {
            throw new IllegalArgumentException("El empleado ya tiene el máximo de tareas concurrentes permitidas.");
        }
    }
}