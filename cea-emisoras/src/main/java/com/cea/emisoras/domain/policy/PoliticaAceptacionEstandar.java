package com.cea.emisoras.domain.policy;

import com.cea.emisoras.domain.valueobject.NivelAceptacion;
import com.cea.emisoras.domain.valueobject.PorcentajeAceptacion;

public class PoliticaAceptacionEstandar implements PoliticaAceptacion {

    private static final double UMBRAL_ALTA = 70;
    private static final double UMBRAL_MEDIA = 40;

    @Override
    public NivelAceptacion clasificar(PorcentajeAceptacion porcentaje) {
        if (porcentaje.valor() >= UMBRAL_ALTA) {
            return NivelAceptacion.ALTA;
        }
        if (porcentaje.valor() >= UMBRAL_MEDIA) {
            return NivelAceptacion.MEDIA;
        }
        return NivelAceptacion.BAJA;
    }
}
