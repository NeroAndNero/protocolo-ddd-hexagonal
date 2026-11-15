package com.cea.emisoras.domain.valueobject;

import java.util.Objects;

import com.cea.emisoras.domain.exception.RadioDatoInvalidoException;
 
public final class Frecuencia {
 
    private final double valor;
 
    public Frecuencia(double valor) {
        if (valor <= 0) {
            throw new RadioDatoInvalidoException("La frecuencia debe ser un valor mayor que cero.");
        }
        this.valor = valor;
    }
 
    public double valor() {
        return valor;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Frecuencia)) return false;
        Frecuencia that = (Frecuencia) o;
        return Double.compare(that.valor, valor) == 0;
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
 
    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}
