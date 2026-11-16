package com.cea.emisoras.domain.valueobject;

import com.cea.emisoras.domain.exception.DatoInvalidoException;
import java.util.Objects;

public final class Consorcio {

    private final RadioId radioId;
    private final ProductoraId productoraId;

    public Consorcio(RadioId radioId, ProductoraId productoraId) {
        if (radioId == null || productoraId == null) {
            throw new DatoInvalidoException("Un consorcio necesita una radio y una compañía productora.");
        }
        this.radioId = radioId;
        this.productoraId = productoraId;
    }

    public RadioId radioId() {
        return radioId;
    }

    public ProductoraId productoraId() {
        return productoraId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Consorcio)) return false;
        Consorcio that = (Consorcio) o;
        return radioId.equals(that.radioId) && productoraId.equals(that.productoraId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(radioId, productoraId);
    }

    @Override
    public String toString() {
        return "Consorcio{radioId=" + radioId + ", productoraId=" + productoraId + "}";
    }
}
