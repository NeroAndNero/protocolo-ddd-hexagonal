package com.cea.emisoras.domain.valueobject;

import com.cea.emisoras.domain.exception.DatoInvalidoException;

import java.util.Objects;
import java.util.UUID;

public final class ProgramaId {

    private final String valor;

    public ProgramaId(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DatoInvalidoException("El id de un Programa no puede estar vacío.");
        }
        this.valor = valor.trim();
    }

    public static ProgramaId generar() {
        return new ProgramaId(UUID.randomUUID().toString());
    }

    public String valor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProgramaId)) return false;
        ProgramaId that = (ProgramaId) o;
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
