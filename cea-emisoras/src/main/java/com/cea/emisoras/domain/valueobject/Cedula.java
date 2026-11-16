package com.cea.emisoras.domain.valueobject;

import com.cea.emisoras.domain.exception.DatoInvalidoException;
import java.util.Objects;
import java.util.regex.Pattern;

public final class Cedula {

    private static final Pattern FORMATO = Pattern.compile("^[0-9]{5,12}$");

    private final String valor;

    public Cedula(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DatoInvalidoException("La cédula no puede estar vacía.");
        }
        String normalizado = valor.trim();
        if (!FORMATO.matcher(normalizado).matches()) {
            throw new DatoInvalidoException("La cédula debe tener entre 5 y 12 dígitos.");
        }
        this.valor = normalizado;
    }

    public String valor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cedula)) return false;
        Cedula that = (Cedula) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}
