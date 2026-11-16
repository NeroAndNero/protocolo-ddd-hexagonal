package com.cea.emisoras.domain.valueobject;

import com.cea.emisoras.domain.exception.DatoInvalidoException;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public final class Rfc {

    private static final Pattern FORMATO = Pattern.compile("^[A-Z0-9&Ñ]{12,13}$");

    private final String valor;

    public Rfc(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DatoInvalidoException("El RFC de la compañía productora no puede estar vacío.");
        }
        String normalizado = valor.trim().toUpperCase(Locale.ROOT);
        if (!FORMATO.matcher(normalizado).matches()) {
            throw new DatoInvalidoException("El RFC debe tener 12 o 13 caracteres alfanuméricos.");
        }
        this.valor = normalizado;
    }

    public ProductoraId comoProductoraId() {
        return new ProductoraId(valor);
    }

    public String valor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rfc)) return false;
        Rfc that = (Rfc) o;
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
