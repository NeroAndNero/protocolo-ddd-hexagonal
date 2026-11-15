package com.cea.emisoras.domain.model;

import com.cea.emisoras.domain.event.EncuestaRegistrada;
import com.cea.emisoras.domain.event.EventoDominio;
import com.cea.emisoras.domain.exception.DatoInvalidoException;
import com.cea.emisoras.domain.valueobject.EmisionId;
import com.cea.emisoras.domain.valueobject.EncuestaId;
import com.cea.emisoras.domain.valueobject.ResultadoEncuesta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Encuesta {

    private final EncuestaId id;
    private final EmisionId emisionId;
    private final ResultadoEncuesta resultado;
    private final List<EventoDominio> eventos = new ArrayList<>();

    private Encuesta(EncuestaId id, EmisionId emisionId, ResultadoEncuesta resultado) {
        this.id = id;
        this.emisionId = emisionId;
        this.resultado = resultado;
    }

    public static Encuesta registrar(EmisionId emisionId, ResultadoEncuesta resultado) {
        if (emisionId == null) {
            throw new DatoInvalidoException("La encuesta debe estar asociada a una emisión.");
        }
        if (resultado == null) {
            throw new DatoInvalidoException("La encuesta debe tener resultados.");
        }
        Encuesta encuesta = new Encuesta(EncuestaId.generar(), emisionId, resultado);
        encuesta.eventos.add(new EncuestaRegistrada(encuesta.id, emisionId, resultado.encuestados()));
        return encuesta;
    }

    public EncuestaId id() {
        return id;
    }

    public EmisionId emisionId() {
        return emisionId;
    }

    public ResultadoEncuesta resultado() {
        return resultado;
    }

    public double porcentajeAprobacion() {
        return resultado.porcentajeAprobacion();
    }

    public List<EventoDominio> eventosPendientes() {
        return List.copyOf(eventos);
    }

    public void limpiarEventos() {
        eventos.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Encuesta)) return false;
        Encuesta encuesta = (Encuesta) o;
        return id.equals(encuesta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Encuesta{id=" + id + ", emisionId=" + emisionId + ", resultado=" + resultado + "}";
    }
}
