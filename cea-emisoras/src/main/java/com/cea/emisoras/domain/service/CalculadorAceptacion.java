package com.cea.emisoras.domain.service;

import com.cea.emisoras.domain.exception.DatoInvalidoException;
import com.cea.emisoras.domain.model.Encuesta;
import com.cea.emisoras.domain.valueobject.PorcentajeAceptacion;

import java.util.List;

public class CalculadorAceptacion {

    public PorcentajeAceptacion calcular(List<Encuesta> encuestas) {
        if (encuestas == null || encuestas.isEmpty()) {
            throw new DatoInvalidoException("Se necesita al menos una encuesta para calcular la aceptación.");
        }
        long aprobaciones = 0;
        long encuestados = 0;
        for (Encuesta encuesta : encuestas) {
            aprobaciones += encuesta.resultado().aprobaciones();
            encuestados += encuesta.resultado().encuestados();
        }
        return new PorcentajeAceptacion(aprobaciones * 100.0 / encuestados);
    }
}
