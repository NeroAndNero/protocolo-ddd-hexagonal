package com.cea.emisoras.domain.event;

import com.cea.emisoras.domain.valueobject.EmisionId;
import com.cea.emisoras.domain.valueobject.EncuestaId;

import java.time.Instant;

public final class EncuestaRegistrada implements EventoDominio {

    private final EncuestaId encuestaId;
    private final EmisionId emisionId;
    private final int totalEncuestados;
    private final Instant ocurridoEn;

    public EncuestaRegistrada(EncuestaId encuestaId, EmisionId emisionId, int totalEncuestados) {
        this.encuestaId = encuestaId;
        this.emisionId = emisionId;
        this.totalEncuestados = totalEncuestados;
        this.ocurridoEn = Instant.now();
    }

    public EncuestaId encuestaId() {
        return encuestaId;
    }

    public EmisionId emisionId() {
        return emisionId;
    }

    public int totalEncuestados() {
        return totalEncuestados;
    }

    @Override
    public Instant ocurridoEn() {
        return ocurridoEn;
    }
}
