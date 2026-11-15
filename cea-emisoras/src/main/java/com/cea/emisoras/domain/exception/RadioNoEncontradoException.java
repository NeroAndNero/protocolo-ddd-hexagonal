package com.cea.emisoras.domain.exception;

public class RadioNoEncontradoException extends RuntimeException {
    public RadioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}