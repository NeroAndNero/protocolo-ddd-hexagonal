package com.neroandnero.cea.proyectos.domain.entity;

import com.neroandnero.cea.proyectos.domain.ProjectStatus;
import com.neroandnero.cea.proyectos.domain.TaskStatus;
import com.neroandnero.cea.proyectos.domain.event.DomainEvent;
import com.neroandnero.cea.proyectos.domain.event.ProjectFinishedDomainEvent;
import com.neroandnero.cea.proyectos.domain.exception.DomainException;
import com.neroandnero.cea.proyectos.domain.exception.ProjectHasPendingTasksException;
import com.neroandnero.cea.proyectos.domain.valueobject.CommercialName;
import com.neroandnero.cea.proyectos.domain.valueobject.EmployeeId;
import com.neroandnero.cea.proyectos.domain.valueobject.ProjectDateRange;
import com.neroandnero.cea.proyectos.domain.valueobject.ProjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectModel {
    private final ProjectId id;
    private final CommercialName commercialName;
    private ProjectDateRange dateRange;
    private ProjectStatus status;
    private final EmployeeId promoterId;
    private final List<TaskModel> tasks;
    private final List<DomainEvent> domainEvents;

    public ProjectModel(ProjectId id, CommercialName commercialName, ProjectDateRange dateRange, EmployeeId promoterId) {
        this.id = id;
        this.commercialName = commercialName;
        this.dateRange = dateRange;
        this.promoterId = promoterId;
        this.status = ProjectStatus.PLANNED; // Todo proyecto inicia planificado
        this.tasks = new ArrayList<>();
        this.domainEvents = new ArrayList<>();
    }

    public void finish(LocalDate endDate) {
        if (this.status == ProjectStatus.FINISHED || this.status == ProjectStatus.CANCELLED) {
            throw new DomainException("El proyecto ya está finalizado o cancelado.");
        }

        long pending = tasks.stream().filter(t -> t.getStatus() != TaskStatus.DONE).count();
        if (pending > 0) {
            throw ProjectHasPendingTasksException.becauseTasksAreNotDone(id.value(), pending);
        }

        this.dateRange = dateRange.withEndDate(endDate); // Valida que fin >= inicio
        this.status = ProjectStatus.FINISHED;

        this.domainEvents.add(new ProjectFinishedDomainEvent(id.value(), endDate));
    }

    public void addTask(TaskModel task) {
        if (this.status == ProjectStatus.FINISHED) {
            throw new DomainException("No se pueden añadir tareas a un proyecto finalizado.");
        }
        this.tasks.add(task);
    }

    
    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return events;
    }

   
    public ProjectId getId() { return id; }
    public CommercialName getCommercialName() { return commercialName; }
    public ProjectStatus getStatus() { return status; }
    public ProjectDateRange getDateRange() { return dateRange; }
}