package com.cea.emisoras.domain.model;

import com.cea.emisoras.domain.exception.DatoInvalidoException;
import com.cea.emisoras.domain.exception.PreferenciaInvalidaException;
import com.cea.emisoras.domain.valueobject.Cedula;
import com.cea.emisoras.domain.valueobject.NombrePersona;
import com.cea.emisoras.domain.valueobject.PersonaId;
import com.cea.emisoras.domain.valueobject.ProductoraId;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonaTest {

    private final ProductoraId productora = new ProductoraId("ABC123456XYZ");

    private Persona persona() {
        return Persona.registrar(new Cedula("1047123456"), new NombrePersona("Ana Pérez"), productora);
    }

    @Test
    void registrarGeneraUnIdDistintoParaCadaPersona() {
        assertNotEquals(persona().id(), persona().id());
    }

    @Test
    void guardaSuCedulaSuNombreYLaCompaniaDondeTrabaja() {
        Persona persona = persona();

        assertEquals(new Cedula("1047123456"), persona.cedula());
        assertEquals(new NombrePersona("Ana Pérez"), persona.nombre());
        assertEquals(productora, persona.productoraId());
        assertTrue(persona.trabajaPara(productora));
        assertFalse(persona.trabajaPara(new ProductoraId("OTRA12345678")));
    }

    @Test
    void rechazaDatosNulos() {
        Cedula cedula = new Cedula("1047123456");
        NombrePersona nombre = new NombrePersona("Ana Pérez");
        PersonaId id = PersonaId.generar();

        assertThrows(DatoInvalidoException.class, () -> new Persona(null, cedula, nombre, productora));
        assertThrows(DatoInvalidoException.class, () -> new Persona(id, null, nombre, productora));
        assertThrows(DatoInvalidoException.class, () -> new Persona(id, cedula, null, productora));
        assertThrows(DatoInvalidoException.class, () -> new Persona(id, cedula, nombre, null));
    }

    @Test
    void trabajaEnUnaSolaCompaniaYAlTrasladarseDejaLaAnterior() {
        Persona persona = persona();
        ProductoraId otra = new ProductoraId("OTRA12345678");

        persona.trasladarA(otra);

        assertEquals(otra, persona.productoraId());
        assertFalse(persona.trabajaPara(productora));
        assertThrows(DatoInvalidoException.class, () -> persona.trasladarA(null));
    }

    @Test
    void sePuedeRenombrar() {
        Persona persona = persona();

        persona.renombrar(new NombrePersona("Ana María Pérez"));

        assertEquals(new NombrePersona("Ana María Pérez"), persona.nombre());
        assertThrows(DatoInvalidoException.class, () -> persona.renombrar(null));
    }

    @Test
    void puedePreferirYDejarDePreferirOperadores() {
        Persona conductor = persona();
        PersonaId operador = PersonaId.generar();

        conductor.preferirOperador(operador);
        assertTrue(conductor.prefiere(operador));
        assertEquals(Set.of(operador), conductor.operadoresPreferidos());

        conductor.quitarPreferencia(operador);
        assertFalse(conductor.prefiere(operador));
        assertTrue(conductor.operadoresPreferidos().isEmpty());
    }

    @Test
    void noPuedePreferirseASiMismaNiUnOperadorNulo() {
        Persona conductor = persona();

        assertThrows(PreferenciaInvalidaException.class, () -> conductor.preferirOperador(conductor.id()));
        assertThrows(PreferenciaInvalidaException.class, () -> conductor.preferirOperador(null));
    }

    @Test
    void dosPersonasConElMismoIdSonLaMismaEntidad() {
        PersonaId id = PersonaId.generar();
        Persona una = new Persona(id, new Cedula("1047123456"), new NombrePersona("Ana"), productora);
        Persona otra = new Persona(id, new Cedula("1047123457"), new NombrePersona("Otra"), productora);

        assertEquals(una, otra);
        assertEquals(una.hashCode(), otra.hashCode());
        assertTrue(una.toString().contains("Ana"));
    }
}
