package com.cea.emisoras.domain.model;

import com.cea.emisoras.domain.exception.ConsorcioInvalidoException;
import com.cea.emisoras.domain.exception.DatoInvalidoException;
import com.cea.emisoras.domain.exception.TipoProduccionInvalidaException;
import com.cea.emisoras.domain.factory.FabricaProgramas;
import com.cea.emisoras.domain.valueobject.Consorcio;
import com.cea.emisoras.domain.valueobject.Genero;
import com.cea.emisoras.domain.valueobject.NombrePrograma;
import com.cea.emisoras.domain.valueobject.PersonaId;
import com.cea.emisoras.domain.valueobject.ProductoraId;
import com.cea.emisoras.domain.valueobject.RadioId;
import com.cea.emisoras.domain.valueobject.RolPersonal;
import com.cea.emisoras.domain.valueobject.TipoProduccion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProgramaConsorcioTest {

    private final PersonaId conductor = PersonaId.generar();
    private final PersonaId operador = PersonaId.generar();
    private final PersonaId productor = PersonaId.generar();

    private Programa programa() {
        return FabricaProgramas.crearNormal(
                new NombrePrograma("Noticias del Caribe"), Genero.PERIODISTICO, conductor, operador, productor);
    }

    private Consorcio consorcio() {
        return new Consorcio(RadioId.generar(), new ProductoraId("ABC123456XYZ"));
    }

    @Test
    void unProgramaNuevoNoTieneConsorcioNiTiposDeProduccion() {
        Programa programa = programa();

        assertTrue(programa.consorcio().isEmpty());
        assertTrue(programa.tipoProduccionDe(productor).isEmpty());
    }

    @Test
    void sePuedeConformarElConsorcioDelPrograma() {
        Programa programa = programa();
        Consorcio consorcio = consorcio();

        programa.conformarConsorcio(consorcio);

        assertEquals(consorcio, programa.consorcio().orElseThrow());
    }

    @Test
    void unProgramaSoloTieneUnConsorcio() {
        Programa programa = programa();
        programa.conformarConsorcio(consorcio());

        assertThrows(ConsorcioInvalidoException.class, () -> programa.conformarConsorcio(consorcio()));
    }

    @Test
    void elConsorcioEsObligatorio() {
        assertThrows(DatoInvalidoException.class, () -> programa().conformarConsorcio(null));
    }

    @Test
    void elProductorDelProgramaPuedeTenerTipoDeProduccion() {
        Programa programa = programa();

        programa.definirTipoProduccion(productor, TipoProduccion.COMERCIAL);
        assertEquals(TipoProduccion.COMERCIAL, programa.tipoProduccionDe(productor).orElseThrow());

        programa.definirTipoProduccion(productor, TipoProduccion.PERIODISTICA);
        assertEquals(TipoProduccion.PERIODISTICA, programa.tipoProduccionDe(productor).orElseThrow());
    }

    @Test
    void soloUnProductorAsignadoPuedeTenerTipoDeProduccion() {
        Programa programa = programa();

        assertThrows(TipoProduccionInvalidaException.class,
                () -> programa.definirTipoProduccion(conductor, TipoProduccion.COMERCIAL));
        assertThrows(TipoProduccionInvalidaException.class,
                () -> programa.definirTipoProduccion(PersonaId.generar(), TipoProduccion.COMERCIAL));
    }

    @Test
    void elProductorYElTipoDeProduccionSonObligatorios() {
        Programa programa = programa();

        assertThrows(DatoInvalidoException.class, () -> programa.definirTipoProduccion(null, TipoProduccion.COMERCIAL));
        assertThrows(DatoInvalidoException.class, () -> programa.definirTipoProduccion(productor, null));
    }

    @Test
    void alRetirarAUnProductorSeOlvidaSuTipoDeProduccion() {
        Programa programa = programa();
        PersonaId otroProductor = PersonaId.generar();
        programa.asignarPersonal(otroProductor, RolPersonal.PRODUCTOR);
        programa.definirTipoProduccion(productor, TipoProduccion.COMERCIAL);

        programa.retirarPersonal(productor, RolPersonal.PRODUCTOR);

        assertTrue(programa.tipoProduccionDe(productor).isEmpty());
        assertFalse(programa.asignaciones().stream().anyMatch(a -> a.personaId().equals(productor)));
    }

    @Test
    void retirarUnConductorNoAfectaLosTiposDeProduccion() {
        Programa programa = programa();
        programa.asignarPersonal(PersonaId.generar(), RolPersonal.CONDUCTOR);
        programa.definirTipoProduccion(productor, TipoProduccion.COMERCIAL);

        programa.retirarPersonal(conductor, RolPersonal.CONDUCTOR);

        assertEquals(TipoProduccion.COMERCIAL, programa.tipoProduccionDe(productor).orElseThrow());
    }
}
