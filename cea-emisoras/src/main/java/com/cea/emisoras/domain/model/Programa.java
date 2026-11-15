package com.cea.emisoras.domain.model;

import com.cea.emisoras.domain.exception.DatoInvalidoException;
import com.cea.emisoras.domain.exception.ProgramaIncompletoException;
import com.cea.emisoras.domain.exception.ProgramaResumenInvalidoException;
import com.cea.emisoras.domain.valueobject.Asignacion;
import com.cea.emisoras.domain.valueobject.Genero;
import com.cea.emisoras.domain.valueobject.NombrePrograma;
import com.cea.emisoras.domain.valueobject.PersonaId;
import com.cea.emisoras.domain.valueobject.ProgramaId;
import com.cea.emisoras.domain.valueobject.RolPersonal;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Programa {

    private final ProgramaId id;
    private final NombrePrograma nombre;
    private final Genero genero;
    private final boolean resumen;
    private final Set<ProgramaId> origenes;
    private Set<Asignacion> asignaciones;

    public Programa(ProgramaId id, NombrePrograma nombre, Genero genero, boolean resumen,
                    Set<ProgramaId> origenes, Set<Asignacion> asignaciones) {
        if (id == null || nombre == null || genero == null || origenes == null || asignaciones == null) {
            throw new DatoInvalidoException("Los datos del programa son obligatorios.");
        }
        validarOrigenes(id, resumen, origenes);
        Set<Asignacion> copiaAsignaciones = new LinkedHashSet<>(asignaciones);
        asegurarEquipoCompleto(copiaAsignaciones);

        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.resumen = resumen;
        this.origenes = new LinkedHashSet<>(origenes);
        this.asignaciones = copiaAsignaciones;
    }

    public void asignarPersonal(PersonaId persona, RolPersonal rol) {
        asignaciones.add(new Asignacion(persona, rol));
    }

    public void retirarPersonal(PersonaId persona, RolPersonal rol) {
        Set<Asignacion> restantes = new LinkedHashSet<>(asignaciones);
        restantes.remove(new Asignacion(persona, rol));
        asegurarEquipoCompleto(restantes);
        this.asignaciones = restantes;
    }

    public boolean tieneRol(RolPersonal rol) {
        return asignaciones.stream().anyMatch(asignacion -> asignacion.rol() == rol);
    }

    public ProgramaId id() {
        return id;
    }

    public NombrePrograma nombre() {
        return nombre;
    }

    public Genero genero() {
        return genero;
    }

    public boolean esResumen() {
        return resumen;
    }

    public Set<ProgramaId> origenes() {
        return Set.copyOf(origenes);
    }

    public Set<Asignacion> asignaciones() {
        return Set.copyOf(asignaciones);
    }

    private static void validarOrigenes(ProgramaId id, boolean resumen, Set<ProgramaId> origenes) {
        if (resumen && origenes.isEmpty()) {
            throw new ProgramaResumenInvalidoException(
                    "Un programa de resumen necesita al menos un programa de origen.");
        }
        if (!resumen && !origenes.isEmpty()) {
            throw new ProgramaResumenInvalidoException(
                    "Un programa que no es de resumen no puede tener programas de origen.");
        }
        if (origenes.contains(id)) {
            throw new ProgramaResumenInvalidoException(
                    "Un programa no puede ser su propio programa de origen.");
        }
    }

    private static void asegurarEquipoCompleto(Set<Asignacion> asignaciones) {
        for (RolPersonal rol : RolPersonal.values()) {
            boolean tieneElRol = asignaciones.stream().anyMatch(asignacion -> asignacion.rol() == rol);
            if (!tieneElRol) {
                throw new ProgramaIncompletoException(
                        "El programa debe conservar al menos un conductor, un operador y un productor.");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Programa)) return false;
        Programa programa = (Programa) o;
        return id.equals(programa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Programa{id=" + id + ", nombre=" + nombre + ", genero=" + genero + ", resumen=" + resumen + "}";
    }
}
