package com.cea.emisoras.domain.valueobject;

import com.cea.emisoras.domain.exception.RadioDatoInvalidoException;
 
public enum TipoTransmision {
 
    AM,
    FM;
 
    public static TipoTransmision desde(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new RadioDatoInvalidoException("El tipo de transmisión no puede estar vacío.");
        }
        try {
            return TipoTransmision.valueOf(valor.trim().toUpperCase());
        } catch (IllegalArgumentException excepcionOriginal) {
            throw new RadioDatoInvalidoException("El tipo de transmisión solo puede ser AM o FM.");
        }
    }
}
