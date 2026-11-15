package com.cea.emisoras.domain.valueobject;

import com.cea.emisoras.domain.exception.DatoInvalidoException;

import java.util.Objects;
import java.util.UUID;

public final class EmisionId {

    private final String valor;

    public EmisionId(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DatoInvalidoException("El id de la emisión no puede estar vacío.");
        }
        this.valor = valor.trim();
    }

    public static EmisionId generar() {
        return new EmisionId(UUID.randomUUID().toString());
    }

    public String valor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmisionId)) return false;
        EmisionId that = (EmisionId) o;
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
