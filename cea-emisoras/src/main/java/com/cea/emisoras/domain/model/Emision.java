package com.cea.emisoras.domain.model;

import com.cea.emisoras.domain.event.EmisionRegistrada;
import com.cea.emisoras.domain.event.EventoDominio;
import com.cea.emisoras.domain.exception.DatoInvalidoException;
import com.cea.emisoras.domain.valueobject.DuracionMinutos;
import com.cea.emisoras.domain.valueobject.EmisionId;
import com.cea.emisoras.domain.valueobject.ProgramaId;
import com.cea.emisoras.domain.valueobject.RadioId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Emision {

    private final EmisionId id;
    private final ProgramaId programaId;
    private final RadioId radioId;
    private final LocalDate fecha;
    private final LocalTime hora;
    private final DuracionMinutos duracion;
    private boolean repeticion;
    private final List<EventoDominio> eventos = new ArrayList<>();

    private Emision(EmisionId id, ProgramaId programaId, RadioId radioId, LocalDate fecha,
                    LocalTime hora, DuracionMinutos duracion, boolean repeticion) {
        this.id = id;
        this.programaId = programaId;
        this.radioId = radioId;
        this.fecha = fecha;
        this.hora = hora;
        this.duracion = duracion;
        this.repeticion = repeticion;
    }

    public static Emision programar(ProgramaId programaId, RadioId radioId, LocalDate fecha,
                                    LocalTime hora, DuracionMinutos duracion, boolean repeticion) {
        if (programaId == null || radioId == null || fecha == null || hora == null || duracion == null) {
            throw new DatoInvalidoException("La emisión necesita programa, radio, fecha, hora y duración.");
        }
        Emision emision = new Emision(EmisionId.generar(), programaId, radioId, fecha, hora, duracion, repeticion);
        emision.eventos.add(new EmisionRegistrada(emision.id, programaId, radioId));
        return emision;
    }

    public void marcarComoRepeticion() {
        this.repeticion = true;
    }

    public EmisionId id() {
        return id;
    }

    public ProgramaId programaId() {
        return programaId;
    }

    public RadioId radioId() {
        return radioId;
    }

    public LocalDate fecha() {
        return fecha;
    }

    public LocalTime hora() {
        return hora;
    }

    public DuracionMinutos duracion() {
        return duracion;
    }

    public boolean esRepeticion() {
        return repeticion;
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
        if (!(o instanceof Emision)) return false;
        Emision emision = (Emision) o;
        return id.equals(emision.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Emision{id=" + id + ", programaId=" + programaId + ", radioId=" + radioId
                + ", fecha=" + fecha + ", hora=" + hora + ", duracion=" + duracion
                + ", repeticion=" + repeticion + "}";
    }
}
