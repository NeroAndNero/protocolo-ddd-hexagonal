package com.neroandnero.cea.proyectos.domain.entity;

import com.neroandnero.cea.proyectos.domain.valueobject.EmployeeId;

public class EmployeeModel {
    private final EmployeeId id;
    private final boolean active;

    public EmployeeModel(EmployeeId id, boolean active) {
        this.id = id;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public EmployeeId getId() {
        return id;
    }
}