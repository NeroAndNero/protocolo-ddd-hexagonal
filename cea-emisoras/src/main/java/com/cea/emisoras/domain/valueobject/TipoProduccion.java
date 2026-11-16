package com.cea.emisoras.domain.valueobject;

import com.cea.emisoras.domain.exception.DatoInvalidoException;

public enum TipoProduccion {
    PERIODISTICA,
    COMERCIAL;

    public static TipoProduccion desde(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new DatoInvalidoException("El tipo de producción no puede estar vacío.");
        }
        String normalizado = texto.trim();
        for (TipoProduccion tipo : values()) {
            if (tipo.name().equalsIgnoreCase(normalizado)) {
                return tipo;
            }
        }
        throw new DatoInvalidoException("El tipo de producción debe ser PERIODISTICA o COMERCIAL.");
    }
}
