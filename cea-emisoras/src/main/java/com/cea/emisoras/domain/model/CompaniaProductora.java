package com.cea.emisoras.domain.model;

import com.cea.emisoras.domain.exception.DatoInvalidoException;
import com.cea.emisoras.domain.valueobject.NombreCompania;
import com.cea.emisoras.domain.valueobject.ProductoraId;
import com.cea.emisoras.domain.valueobject.Rfc;
import com.cea.emisoras.domain.valueobject.Telefono;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class CompaniaProductora {

    private final Rfc rfc;
    private NombreCompania nombre;
    private final Set<Telefono> telefonos;

    public CompaniaProductora(Rfc rfc, NombreCompania nombre, Set<Telefono> telefonos) {
        if (rfc == null || nombre == null || telefonos == null) {
            throw new DatoInvalidoException("Los datos de la compañía productora son obligatorios.");
        }
        if (telefonos.isEmpty()) {
            throw new DatoInvalidoException("La compañía productora necesita al menos un teléfono.");
        }
        this.rfc = rfc;
        this.nombre = nombre;
        this.telefonos = new LinkedHashSet<>(telefonos);
    }

    public void renombrar(NombreCompania nuevoNombre) {
        if (nuevoNombre == null) {
            throw new DatoInvalidoException("El nuevo nombre de la compañía es obligatorio.");
        }
        this.nombre = nuevoNombre;
    }

    public void agregarTelefono(Telefono telefono) {
        if (telefono == null) {
            throw new DatoInvalidoException("El teléfono es obligatorio.");
        }
        telefonos.add(telefono);
    }

    public void quitarTelefono(Telefono telefono) {
        if (telefono == null) {
            throw new DatoInvalidoException("El teléfono es obligatorio.");
        }
        if (telefonos.contains(telefono) && telefonos.size() == 1) {
            throw new DatoInvalidoException("La compañía productora debe conservar al menos un teléfono.");
        }
        telefonos.remove(telefono);
    }

    public ProductoraId productoraId() {
        return rfc.comoProductoraId();
    }

    public Rfc rfc() {
        return rfc;
    }

    public NombreCompania nombre() {
        return nombre;
    }

    public Set<Telefono> telefonos() {
        return Set.copyOf(telefonos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompaniaProductora)) return false;
        CompaniaProductora that = (CompaniaProductora) o;
        return rfc.equals(that.rfc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rfc);
    }

    @Override
    public String toString() {
        return "CompaniaProductora{rfc=" + rfc + ", nombre=" + nombre + "}";
    }
}
