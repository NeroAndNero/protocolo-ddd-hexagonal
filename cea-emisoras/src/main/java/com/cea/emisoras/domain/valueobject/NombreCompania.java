package com.cea.emisoras.domain.valueobject;

import com.cea.emisoras.domain.exception.DatoInvalidoException;
import java.util.Objects;

public final class NombreCompania {

    private static final int LONGITUD_MAXIMA = 100;

    private final String valor;

    public NombreCompania(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DatoInvalidoException("El nombre de la compañía productora no puede estar vacío.");
        }
        if (valor.trim().length() > LONGITUD_MAXIMA) {
            throw new DatoInvalidoException(
                    "El nombre de la compañía productora no puede superar " + LONGITUD_MAXIMA + " caracteres.");
        }
        this.valor = valor.trim();
    }

    public String valor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NombreCompania)) return false;
        NombreCompania that = (NombreCompania) o;
        return valor.equalsIgnoreCase(that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor.toLowerCase());
    }

    @Override
    public String toString() {
        return valor;
    }
}
