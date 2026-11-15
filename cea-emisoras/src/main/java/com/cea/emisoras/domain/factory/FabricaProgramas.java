package com.cea.emisoras.domain.factory;

import com.cea.emisoras.domain.exception.ProgramaResumenInvalidoException;
import com.cea.emisoras.domain.model.Programa;
import com.cea.emisoras.domain.valueobject.Asignacion;
import com.cea.emisoras.domain.valueobject.Genero;
import com.cea.emisoras.domain.valueobject.NombrePrograma;
import com.cea.emisoras.domain.valueobject.PersonaId;
import com.cea.emisoras.domain.valueobject.ProgramaId;
import com.cea.emisoras.domain.valueobject.RolPersonal;

import java.util.LinkedHashSet;
import java.util.Set;

public final class FabricaProgramas {

    private FabricaProgramas() {
    }

    public static Programa crearNormal(NombrePrograma nombre, Genero genero,
                                       PersonaId conductor, PersonaId operador, PersonaId productor) {
        return new Programa(ProgramaId.generar(), nombre, genero, false,
                Set.of(), equipo(conductor, operador, productor));
    }

    public static Programa crearResumen(NombrePrograma nombre, Genero genero,
                                        PersonaId conductor, PersonaId operador, PersonaId productor,
                                        Set<ProgramaId> origenes) {
        if (origenes == null || origenes.isEmpty()) {
            throw new ProgramaResumenInvalidoException(
                    "Un programa de resumen necesita al menos un programa de origen.");
        }
        return new Programa(ProgramaId.generar(), nombre, genero, true,
                origenes, equipo(conductor, operador, productor));
    }

    private static Set<Asignacion> equipo(PersonaId conductor, PersonaId operador, PersonaId productor) {
        Set<Asignacion> equipo = new LinkedHashSet<>();
        equipo.add(new Asignacion(conductor, RolPersonal.CONDUCTOR));
        equipo.add(new Asignacion(operador, RolPersonal.OPERADOR));
        equipo.add(new Asignacion(productor, RolPersonal.PRODUCTOR));
        return equipo;
    }
}
