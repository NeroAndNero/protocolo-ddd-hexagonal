package com.cea.emisoras.domain.policy;

import com.cea.emisoras.domain.valueobject.NivelAceptacion;
import com.cea.emisoras.domain.valueobject.PorcentajeAceptacion;

public interface PoliticaAceptacion {

    NivelAceptacion clasificar(PorcentajeAceptacion porcentaje);
}
