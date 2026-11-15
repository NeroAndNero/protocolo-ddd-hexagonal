package com.cea.emisoras.domain.exception;

public class RadioDatoInvalidoException extends RuntimeException {

    public RadioDatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}