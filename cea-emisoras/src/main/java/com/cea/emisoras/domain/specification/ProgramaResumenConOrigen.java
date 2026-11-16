package com.cea.emisoras.domain.specification;

import com.cea.emisoras.domain.model.Programa;

public class ProgramaResumenConOrigen implements Especificacion<Programa> {

    @Override
    public boolean esSatisfechaPor(Programa programa) {
        return programa.esResumen() && !programa.origenes().isEmpty();
    }
}
