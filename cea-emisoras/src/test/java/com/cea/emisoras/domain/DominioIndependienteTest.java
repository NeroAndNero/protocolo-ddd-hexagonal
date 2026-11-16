package com.cea.emisoras.domain;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DominioIndependienteTest {

    private static final Path RAIZ_DOMINIO =
            Path.of("src", "main", "java", "com", "cea", "emisoras", "domain");

    @Test
    void elDominioSoloImportaClasesDeJavaYDeSuPropioPaquete() throws IOException {
        List<String> infracciones = new ArrayList<>();

        for (Path archivo : archivosDelDominio()) {
            for (String linea : Files.readAllLines(archivo)) {
                String limpia = linea.trim();
                if (limpia.startsWith("import ")) {
                    String importado = limpia.replace("import static ", "").replace("import ", "");
                    boolean permitido = importado.startsWith("java.")
                            || importado.startsWith("com.cea.emisoras.domain.");
                    if (!permitido) {
                        infracciones.add(archivo.getFileName() + " -> " + importado);
                    }
                }
            }
        }

        assertTrue(infracciones.isEmpty(), "Imports no permitidos en el dominio: " + infracciones);
    }

    @Test
    void elDominioTieneArchivosParaVerificar() throws IOException {
        assertFalse(archivosDelDominio().isEmpty());
    }

    private static List<Path> archivosDelDominio() throws IOException {
        try (Stream<Path> archivos = Files.walk(RAIZ_DOMINIO)) {
            return archivos.filter(ruta -> ruta.toString().endsWith(".java")).collect(Collectors.toList());
        }
    }
}
