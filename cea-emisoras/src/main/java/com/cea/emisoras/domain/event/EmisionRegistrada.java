package com.cea.emisoras.domain.event;

import com.cea.emisoras.domain.valueobject.EmisionId;
import com.cea.emisoras.domain.valueobject.ProgramaId;
import com.cea.emisoras.domain.valueobject.RadioId;

import java.time.Instant;

public final class EmisionRegistrada implements EventoDominio {

    private final EmisionId emisionId;
    private final ProgramaId programaId;
    private final RadioId radioId;
    private final Instant ocurridoEn;

    public EmisionRegistrada(EmisionId emisionId, ProgramaId programaId, RadioId radioId) {
        this.emisionId = emisionId;
        this.programaId = programaId;
        this.radioId = radioId;
        this.ocurridoEn = Instant.now();
    }

    public EmisionId emisionId() {
        return emisionId;
    }

    public ProgramaId programaId() {
        return programaId;
    }

    public RadioId radioId() {
        return radioId;
    }

    @Override
    public Instant ocurridoEn() {
        return ocurridoEn;
    }
}
