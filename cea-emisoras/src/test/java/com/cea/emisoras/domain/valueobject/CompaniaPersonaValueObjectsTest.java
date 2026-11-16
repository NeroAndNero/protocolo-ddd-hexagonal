package com.cea.emisoras.domain.valueobject;

import com.cea.emisoras.domain.exception.DatoInvalidoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CompaniaPersonaValueObjectsTest {

    @Test
    void elRfcSeNormalizaAMayusculasYSinEspacios() {
        assertEquals("ABC123456XYZ", new Rfc("  abc123456xyz ").valor());
    }

    @Test
    void elRfcAceptaDoceOTreceCaracteres() {
        assertEquals(12, new Rfc("ABC123456XYZ").valor().length());
        assertEquals(13, new Rfc("ABCD123456XYZ").valor().length());
    }

    @Test
    void elRfcRechazaVacioOFormatoInvalido() {
        assertThrows(DatoInvalidoException.class, () -> new Rfc(null));
        assertThrows(DatoInvalidoException.class, () -> new Rfc("  "));
        assertThrows(DatoInvalidoException.class, () -> new Rfc("CORTO"));
        assertThrows(DatoInvalidoException.class, () -> new Rfc("ABC123456XY-Z"));
    }

    @Test
    void elRfcSeConvierteEnProductoraId() {
        assertEquals(new ProductoraId("ABC123456XYZ"), new Rfc("abc123456xyz").comoProductoraId());
    }

    @Test
    void dosRfcConElMismoValorSonIguales() {
        assertEquals(new Rfc("abc123456xyz"), new Rfc("ABC123456XYZ"));
        assertEquals(new Rfc("abc123456xyz").hashCode(), new Rfc("ABC123456XYZ").hashCode());
        assertNotEquals(new Rfc("ABC123456XYZ"), new Rfc("ABC123456XYW"));
    }

    @Test
    void elNombreDeLaCompaniaSeRecortaYSeCompareSinMayusculas() {
        assertEquals("Caribe Media", new NombreCompania("  Caribe Media ").valor());
        assertEquals(new NombreCompania("caribe media"), new NombreCompania("CARIBE MEDIA"));
        assertEquals(new NombreCompania("caribe media").hashCode(), new NombreCompania("CARIBE MEDIA").hashCode());
    }

    @Test
    void elNombreDeLaCompaniaRechazaVacioOMuyLargo() {
        assertThrows(DatoInvalidoException.class, () -> new NombreCompania(null));
        assertThrows(DatoInvalidoException.class, () -> new NombreCompania(" "));
        assertThrows(DatoInvalidoException.class, () -> new NombreCompania("a".repeat(101)));
    }

    @Test
    void elTelefonoSeNormalizaQuitandoSeparadores() {
        assertEquals("+573001234567", new Telefono("+57 300-123 4567").valor());
        assertEquals("6051234567", new Telefono("(605) 123-4567").valor());
    }

    @Test
    void elTelefonoRechazaVacioLetrasOLongitudInvalida() {
        assertThrows(DatoInvalidoException.class, () -> new Telefono(null));
        assertThrows(DatoInvalidoException.class, () -> new Telefono(" "));
        assertThrows(DatoInvalidoException.class, () -> new Telefono("12345"));
        assertThrows(DatoInvalidoException.class, () -> new Telefono("abc1234567"));
        assertThrows(DatoInvalidoException.class, () -> new Telefono("1".repeat(16)));
    }

    @Test
    void dosTelefonosNormalizadosIgualesSonIguales() {
        assertEquals(new Telefono("300 123 4567"), new Telefono("3001234567"));
        assertEquals(new Telefono("300 123 4567").hashCode(), new Telefono("3001234567").hashCode());
    }

    @Test
    void laCedulaAceptaSoloDigitosEntreCincoYDoce() {
        assertEquals("1047123456", new Cedula(" 1047123456 ").valor());
        assertThrows(DatoInvalidoException.class, () -> new Cedula(null));
        assertThrows(DatoInvalidoException.class, () -> new Cedula(" "));
        assertThrows(DatoInvalidoException.class, () -> new Cedula("1234"));
        assertThrows(DatoInvalidoException.class, () -> new Cedula("12A456"));
        assertThrows(DatoInvalidoException.class, () -> new Cedula("1".repeat(13)));
    }

    @Test
    void dosCedulasConElMismoValorSonIguales() {
        assertEquals(new Cedula("1047123456"), new Cedula("1047123456"));
        assertEquals(new Cedula("1047123456").hashCode(), new Cedula("1047123456").hashCode());
        assertNotEquals(new Cedula("1047123456"), new Cedula("1047123457"));
    }

    @Test
    void elNombreDeLaPersonaSeRecortaYRechazaVacioOMuyLargo() {
        assertEquals("Ana Pérez", new NombrePersona("  Ana Pérez ").valor());
        assertEquals(new NombrePersona("ana pérez"), new NombrePersona("ANA PÉREZ"));
        assertEquals(new NombrePersona("ana pérez").hashCode(), new NombrePersona("ANA PÉREZ").hashCode());
        assertThrows(DatoInvalidoException.class, () -> new NombrePersona(null));
        assertThrows(DatoInvalidoException.class, () -> new NombrePersona(" "));
        assertThrows(DatoInvalidoException.class, () -> new NombrePersona("a".repeat(101)));
    }

    @Test
    void elTipoDeProduccionSeInterpretaSinImportarMayusculasNiEspacios() {
        assertEquals(TipoProduccion.PERIODISTICA, TipoProduccion.desde(" periodistica "));
        assertEquals(TipoProduccion.COMERCIAL, TipoProduccion.desde("COMERCIAL"));
        assertThrows(DatoInvalidoException.class, () -> TipoProduccion.desde(null));
        assertThrows(DatoInvalidoException.class, () -> TipoProduccion.desde(" "));
        assertThrows(DatoInvalidoException.class, () -> TipoProduccion.desde("OTRO"));
    }

    @Test
    void elConsorcioNecesitaUnaRadioYUnaProductora() {
        RadioId radio = RadioId.generar();
        ProductoraId productora = new ProductoraId("ABC123456XYZ");
        Consorcio consorcio = new Consorcio(radio, productora);

        assertEquals(radio, consorcio.radioId());
        assertEquals(productora, consorcio.productoraId());
        assertThrows(DatoInvalidoException.class, () -> new Consorcio(null, productora));
        assertThrows(DatoInvalidoException.class, () -> new Consorcio(radio, null));
    }

    @Test
    void dosConsorciosConLosMismosParticipantesSonIguales() {
        RadioId radio = RadioId.generar();
        ProductoraId productora = new ProductoraId("ABC123456XYZ");

        assertEquals(new Consorcio(radio, productora), new Consorcio(radio, productora));
        assertEquals(new Consorcio(radio, productora).hashCode(), new Consorcio(radio, productora).hashCode());
        assertNotEquals(new Consorcio(radio, productora), new Consorcio(RadioId.generar(), productora));
    }
}
