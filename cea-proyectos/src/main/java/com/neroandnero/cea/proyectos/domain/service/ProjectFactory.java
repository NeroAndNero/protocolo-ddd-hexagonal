package com.neroandnero.cea.proyectos.domain.service;

import com.neroandnero.cea.proyectos.domain.entity.ProjectModel;
import com.neroandnero.cea.proyectos.domain.valueobject.*;
import java.time.LocalDate;

public class ProjectFactory {

    public static ProjectModel createNewProject(String codeName, String commercialName, LocalDate startDate, String promoterDni) {
        ProjectId id = new ProjectId(codeName);
        CommercialName name = new CommercialName(commercialName);
        ProjectDateRange dateRange = new ProjectDateRange(startDate, null);
        EmployeeId promoter = new EmployeeId(promoterDni);

        return new ProjectModel(id, name, dateRange, promoter);
    }
}