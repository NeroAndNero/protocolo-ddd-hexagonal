package com.cea.emisoras.domain.model;

import com.cea.emisoras.domain.exception.DatoInvalidoException;
import com.cea.emisoras.domain.valueobject.NombreCompania;
import com.cea.emisoras.domain.valueobject.Rfc;
import com.cea.emisoras.domain.valueobject.Telefono;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompaniaProductoraTest {

    private final Rfc rfc = new Rfc("ABC123456XYZ");
    private final Telefono telefono = new Telefono("3001234567");

    private CompaniaProductora compania() {
        return new CompaniaProductora(rfc, new NombreCompania("Caribe Media"), Set.of(telefono));
    }

    @Test
    void sePuedeCrearConRfcNombreYUnTelefono() {
        CompaniaProductora compania = compania();

        assertEquals(rfc, compania.rfc());
        assertEquals(new NombreCompania("Caribe Media"), compania.nombre());
        assertEquals(Set.of(telefono), compania.telefonos());
    }

    @Test
    void suProductoraIdSaleDelRfc() {
        assertEquals(rfc.comoProductoraId(), compania().productoraId());
    }

    @Test
    void rechazaDatosNulosYCompaniasSinTelefonos() {
        NombreCompania nombre = new NombreCompania("Caribe Media");

        assertThrows(DatoInvalidoException.class, () -> new CompaniaProductora(null, nombre, Set.of(telefono)));
        assertThrows(DatoInvalidoException.class, () -> new CompaniaProductora(rfc, null, Set.of(telefono)));
        assertThrows(DatoInvalidoException.class, () -> new CompaniaProductora(rfc, nombre, null));
        assertThrows(DatoInvalidoException.class, () -> new CompaniaProductora(rfc, nombre, Set.of()));
    }

    @Test
    void sePuedeRenombrar() {
        CompaniaProductora compania = compania();

        compania.renombrar(new NombreCompania("Nuevo Nombre"));

        assertEquals(new NombreCompania("Nuevo Nombre"), compania.nombre());
        assertThrows(DatoInvalidoException.class, () -> compania.renombrar(null));
    }

    @Test
    void sePuedeAgregarYQuitarTelefonosSiConservaAlMenosUno() {
        CompaniaProductora compania = compania();
        Telefono otro = new Telefono("6051234567");

        compania.agregarTelefono(otro);
        assertTrue(compania.telefonos().contains(otro));

        compania.quitarTelefono(telefono);
        assertFalse(compania.telefonos().contains(telefono));
        assertEquals(Set.of(otro), compania.telefonos());
    }

    @Test
    void noPermiteQuitarElUnicoTelefono() {
        CompaniaProductora compania = compania();

        assertThrows(DatoInvalidoException.class, () -> compania.quitarTelefono(telefono));
        assertEquals(Set.of(telefono), compania.telefonos());
    }

    @Test
    void quitarUnTelefonoQueNoTieneNoCambiaNada() {
        CompaniaProductora compania = compania();

        compania.quitarTelefono(new Telefono("6051234567"));

        assertEquals(Set.of(telefono), compania.telefonos());
    }

    @Test
    void rechazaTelefonosNulos() {
        CompaniaProductora compania = compania();

        assertThrows(DatoInvalidoException.class, () -> compania.agregarTelefono(null));
        assertThrows(DatoInvalidoException.class, () -> compania.quitarTelefono(null));
    }

    @Test
    void dosCompaniasConElMismoRfcSonLaMismaEntidad() {
        CompaniaProductora una = compania();
        CompaniaProductora otra = new CompaniaProductora(rfc, new NombreCompania("Otro Nombre"), Set.of(telefono));
        CompaniaProductora distinta = new CompaniaProductora(
                new Rfc("ABC123456XYW"), new NombreCompania("Caribe Media"), Set.of(telefono));

        assertEquals(una, otra);
        assertEquals(una.hashCode(), otra.hashCode());
        assertNotEquals(una, distinta);
        assertTrue(una.toString().contains("ABC123456XYZ"));
    }
}
