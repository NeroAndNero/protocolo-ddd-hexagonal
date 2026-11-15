package com.neroandnero.cea.proyectos.domain.policy;

import com.neroandnero.cea.proyectos.domain.valueobject.TaskPeriod;
import java.util.List;

public interface AssignmentConflictPolicy {
    boolean canAssign(List<TaskPeriod> existingCommitments);
}