package com.cea.emisoras.domain.model;

import com.cea.emisoras.domain.exception.DatoInvalidoException;
import com.cea.emisoras.domain.exception.PreferenciaInvalidaException;
import com.cea.emisoras.domain.valueobject.Cedula;
import com.cea.emisoras.domain.valueobject.NombrePersona;
import com.cea.emisoras.domain.valueobject.PersonaId;
import com.cea.emisoras.domain.valueobject.ProductoraId;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Persona {

    private final PersonaId id;
    private final Cedula cedula;
    private NombrePersona nombre;
    private ProductoraId productoraId;
    private final Set<PersonaId> operadoresPreferidos;

    public Persona(PersonaId id, Cedula cedula, NombrePersona nombre, ProductoraId productoraId) {
        if (id == null || cedula == null || nombre == null || productoraId == null) {
            throw new DatoInvalidoException("Los datos de la persona son obligatorios.");
        }
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.productoraId = productoraId;
        this.operadoresPreferidos = new LinkedHashSet<>();
    }

    public static Persona registrar(Cedula cedula, NombrePersona nombre, ProductoraId productoraId) {
        return new Persona(PersonaId.generar(), cedula, nombre, productoraId);
    }

    public void renombrar(NombrePersona nuevoNombre) {
        if (nuevoNombre == null) {
            throw new DatoInvalidoException("El nuevo nombre de la persona es obligatorio.");
        }
        this.nombre = nuevoNombre;
    }

    public void trasladarA(ProductoraId nuevaProductora) {
        if (nuevaProductora == null) {
            throw new DatoInvalidoException("La nueva compañía productora es obligatoria.");
        }
        this.productoraId = nuevaProductora;
    }

    public void preferirOperador(PersonaId operador) {
        if (operador == null) {
            throw new PreferenciaInvalidaException("El operador preferido es obligatorio.");
        }
        if (operador.equals(id)) {
            throw new PreferenciaInvalidaException("Una persona no puede preferirse a sí misma como operador.");
        }
        operadoresPreferidos.add(operador);
    }

    public void quitarPreferencia(PersonaId operador) {
        operadoresPreferidos.remove(operador);
    }

    public boolean prefiere(PersonaId operador) {
        return operadoresPreferidos.contains(operador);
    }

    public boolean trabajaPara(ProductoraId productora) {
        return productoraId.equals(productora);
    }

    public PersonaId id() {
        return id;
    }

    public Cedula cedula() {
        return cedula;
    }

    public NombrePersona nombre() {
        return nombre;
    }

    public ProductoraId productoraId() {
        return productoraId;
    }

    public Set<PersonaId> operadoresPreferidos() {
        return Set.copyOf(operadoresPreferidos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona)) return false;
        Persona that = (Persona) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Persona{id=" + id + ", cedula=" + cedula + ", nombre=" + nombre + "}";
    }
}
