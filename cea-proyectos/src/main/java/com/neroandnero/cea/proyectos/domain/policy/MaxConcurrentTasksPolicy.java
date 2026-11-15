package com.neroandnero.cea.proyectos.domain.policy;

import com.neroandnero.cea.proyectos.domain.valueobject.TaskPeriod;
import java.util.List;

public class MaxConcurrentTasksPolicy implements AssignmentConflictPolicy {
    
    private final int maxTasks;

    public MaxConcurrentTasksPolicy(int maxTasks) {
        this.maxTasks = maxTasks;
    }

    @Override
    public boolean canAssign(List<TaskPeriod> existingCommitments) {
        return existingCommitments.size() < maxTasks;
    }
}