package com.neroandnero.cea.proyectos.domain.event;

import java.time.LocalDate;

public record ProjectFinishedDomainEvent(String projectId, LocalDate endDate) implements DomainEvent {
}