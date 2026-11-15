package com.neroandnero.cea.proyectos.domain.entity;

import com.neroandnero.cea.proyectos.domain.valueobject.TaskDuration;
import com.neroandnero.cea.proyectos.domain.TaskStatus;
import java.util.UUID;

public class TaskModel {
    private final String id;
    private final String description;
    private final TaskDuration duration;
    private TaskStatus status;

    public TaskModel(String description, TaskDuration duration) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.duration = duration;
        this.status = TaskStatus.TODO;
    }

    public boolean isDone() {
        return this.status == TaskStatus.DONE;
    }

    public void markAsDone() {
        this.status = TaskStatus.DONE;
    }

    public String getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }
}