package com.cea.emisoras.domain.model;
 
import com.cea.emisoras.domain.valueobject.Frecuencia;
import com.cea.emisoras.domain.valueobject.NombreRadio;
import com.cea.emisoras.domain.valueobject.RadioId;
import com.cea.emisoras.domain.valueobject.TipoTransmision;
 
import java.util.Objects;
 
public class Radio {
 
    private final RadioId id;
    private NombreRadio nombre;
    private Frecuencia frecuencia;
    private TipoTransmision tipoTransmision;
 
    public Radio(RadioId id, NombreRadio nombre, Frecuencia frecuencia, TipoTransmision tipoTransmision) {
        this.id = id;
        this.nombre = nombre;
        this.frecuencia = frecuencia;
        this.tipoTransmision = tipoTransmision;
    }
 
    public static Radio registrar(NombreRadio nombre, Frecuencia frecuencia, TipoTransmision tipoTransmision) {
        return new Radio(RadioId.generar(), nombre, frecuencia, tipoTransmision);
    }
 
    public void actualizarDatos(NombreRadio nuevoNombre, Frecuencia nuevaFrecuencia, TipoTransmision nuevoTipo) {
        this.nombre = nuevoNombre;
        this.frecuencia = nuevaFrecuencia;
        this.tipoTransmision = nuevoTipo;
    }
 
    public RadioId id() {
        return id;
    }
 
    public NombreRadio nombre() {
        return nombre;
    }
 
    public Frecuencia frecuencia() {
        return frecuencia;
    }
 
    public TipoTransmision tipoTransmision() {
        return tipoTransmision;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Radio)) return false;
        Radio radio = (Radio) o;
        return id.equals(radio.id);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
 
    @Override
    public String toString() {
        return "Radio{" +
                "id=" + id +
                ", nombre=" + nombre +
                ", frecuencia=" + frecuencia +
                ", tipoTransmision=" + tipoTransmision +
                '}';
    }
}
