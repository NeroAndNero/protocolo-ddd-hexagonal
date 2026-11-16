package com.cea.emisoras.domain.specification;

import com.cea.emisoras.domain.model.Programa;
import com.cea.emisoras.domain.valueobject.RolPersonal;

public class ProgramaConEquipoCompleto implements Especificacion<Programa> {

    @Override
    public boolean esSatisfechaPor(Programa programa) {
        return programa.tieneRol(RolPersonal.CONDUCTOR)
                && programa.tieneRol(RolPersonal.OPERADOR)
                && programa.tieneRol(RolPersonal.PRODUCTOR);
    }
}
