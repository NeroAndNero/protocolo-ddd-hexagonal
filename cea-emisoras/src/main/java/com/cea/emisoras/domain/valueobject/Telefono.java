package com.cea.emisoras.domain.valueobject;

import com.cea.emisoras.domain.exception.DatoInvalidoException;
import java.util.Objects;
import java.util.regex.Pattern;

public final class Telefono {

    private static final Pattern FORMATO = Pattern.compile("^\\+?[0-9]{7,15}$");

    private final String valor;

    public Telefono(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DatoInvalidoException("El teléfono no puede estar vacío.");
        }
        String normalizado = valor.replaceAll("[\\s\\-()]", "");
        if (!FORMATO.matcher(normalizado).matches()) {
            throw new DatoInvalidoException("El teléfono debe tener entre 7 y 15 dígitos.");
        }
        this.valor = normalizado;
    }

    public String valor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Telefono)) return false;
        Telefono that = (Telefono) o;
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
