package com.cea.emisoras.domain.service;

import com.cea.emisoras.domain.exception.DatoInvalidoException;
import com.cea.emisoras.domain.exception.PersonalEnVariasProductorasException;
import com.cea.emisoras.domain.valueobject.PersonaId;
import com.cea.emisoras.domain.valueobject.ProductoraId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class VerificadorExclusividadPersonalTest {

    private final VerificadorExclusividadPersonal verificador = new VerificadorExclusividadPersonal();
    private final PersonaId persona = PersonaId.generar();
    private final ProductoraId productora = ProductoraId.generar();

    @Test
    void permiteAsignarSiLaPersonaNoTrabajaEnNingunaProductora() {
        verificador.verificar(persona, productora, List.of());
    }

    @Test
    void permiteAsignarSiLaPersonaSoloTrabajaEnLaMismaProductora() {
        verificador.verificar(persona, productora, List.of(productora, productora));
    }

    @Test
    void rechazaAsignarSiLaPersonaYaTrabajaParaOtraProductora() {
        ProductoraId otra = ProductoraId.generar();

        assertThrows(PersonalEnVariasProductorasException.class,
                () -> verificador.verificar(persona, productora, List.of(productora, otra)));
    }

    @Test
    void rechazaDatosIncompletos() {
        assertThrows(DatoInvalidoException.class, () -> verificador.verificar(null, productora, List.of()));
        assertThrows(DatoInvalidoException.class, () -> verificador.verificar(persona, null, List.of()));
        assertThrows(DatoInvalidoException.class, () -> verificador.verificar(persona, productora, null));
    }
}
