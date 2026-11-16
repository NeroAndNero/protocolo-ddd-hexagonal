# CEA Emisoras - Capa de Dominio (DDD)

Este módulo contiene la capa de dominio para el sistema de gestión de Emisoras (CEA). Fue desarrollado aplicando los principios de **Domain-Driven Design (DDD)**, garantizando que la lógica de negocio esté completamente aislada de frameworks, bases de datos o interfaces de usuario.

Actualmente, el proyecto se enfoca exclusivamente en el paquete `/domain`; las capas de aplicación e infraestructura se añadirán en futuras iteraciones.

## Estructura del paquete `/domain`

El código está organizado en subpaquetes que separan las responsabilidades tácticas de DDD:

```text
com.cea.emisoras.domain
├── exception/     -> Excepciones de negocio (ej. ProgramaIncompletoException)
├── valueobject/   -> Objetos inmutables validados en su creación (ej. Frecuencia, Rfc)
├── event/         -> Eventos de dominio (ej. EncuestaRegistrada)
├── model/         -> Entidades y raíces de agregado (ej. Radio, Programa)
├── factory/       -> Fábricas para creación compleja de agregados (ej. FabricaProgramas)
├── service/       -> Lógica de negocio que cruza múltiples agregados
├── policy/        -> Patrón Strategy para reglas intercambiables
└── specification/ -> Patrón Specification para evaluar condiciones lógicas
```

### ¿Por qué están separados?

La separación sigue las dependencias reales del código, asegurando que no existan ciclos y que el núcleo (`model`) no dependa de las capas externas (`service`, `policy`, `factory` o `specification`):

- **exception:** no depende de nada. Contiene los errores base.
- **valueobject:** depende de `exception`. Son los bloques de construcción básicos.
- **event:** depende de `valueobject`. Notifica hechos ocurridos.
- **model:** depende de `event`, `exception` y `valueobject`. Es el núcleo del negocio.
- **policy:** depende de `valueobject`. Aísla las reglas de decisión.
- **service:** depende de `exception`, `model` y `valueobject`. Coordina lógica transversal.
- **specification:** depende de `model` y `valueobject`. Evalúa condiciones sobre las entidades.
- **factory:** depende de `exception`, `model` y `valueobject`. Encapsula la creación compleja.

## Aislamiento del dominio

El dominio está completamente aislado de infraestructura. Esto está respaldado por la prueba unitaria `DominioIndependienteTest`, la cual lee los archivos `.java` del paquete `/domain` y comprueba que sus líneas `import` apunten únicamente a clases nativas de Java (`java.*`) o a clases del propio dominio, prohibiendo cualquier dependencia de frameworks externos.

## Reglas de negocio del CEA (Emisoras)

Las reglas específicas del negocio están protegidas dentro de las siguientes clases:

- **Equipo mínimo de programa:** todo programa debe tener al menos un conductor, un operador y un productor. Esta regla se comprueba en el constructor del programa y al retirar personal, mediante el método privado `asegurarEquipoCompleto`, lanzando `ProgramaIncompletoException` si falta un rol.
- **Exclusividad de personal:** una persona trabaja en una sola compañía. Esto se garantiza porque la entidad `Persona` guarda un único `productoraId`. Además, el servicio `VerificadorExclusividadPersonal` recibe una persona, la productora del programa y la lista de productoras donde ya trabaja; si encuentra alguna distinta a la del programa, lanza `PersonalEnVariasProductorasException`.
- **Programas de resumen:** un programa de tipo resumen necesita obligatoriamente al menos un programa de origen. Si no lo tiene, se lanza `ProgramaResumenInvalidoException`.
- **Consorcios:** un programa tiene asignado un único consorcio. Si se intenta conformar un consorcio cuando el programa ya tiene uno, se lanza `ConsorcioInvalidoException`. Si el consorcio no trae radio o productora, se lanza `DatoInvalidoException`.
- **Datos de compañía:** una compañía productora debe conservar al menos un teléfono registrado en todo momento.

> **Nota:** los formatos específicos de RFC y teléfono son decisiones de diseño del equipo para esta iteración.

## Patrones tácticos implementados

- **Entidades:** objetos con identidad propia (`Radio`, `CompaniaProductora`, `Persona`).
- **Agregados:** entidades raíz que protegen invariantes (`Programa`, `Emision`, `Encuesta`).
- **Value Objects:** clases inmutables sin identidad (`Frecuencia`, `Rfc`, `Cedula`, `ResultadoEncuesta`).
- **Eventos de dominio:** hechos ocurridos en el pasado (`EncuestaRegistrada`, `EmisionRegistrada`).
- **Fábricas:** centralizan la creación de agregados complejos (`FabricaProgramas`).
- **Servicios de dominio:** lógica transversal (`CalculadorAceptacion`, `VerificadorExclusividadPersonal`).
- **Políticas (Strategy):** reglas sustituibles (`PoliticaAceptacionEstandar`).
- **Especificaciones (Specification):** permiten evaluar si un `Programa` cumple una condición lógica (ej. `ProgramaConEquipoCompleto`) y se pueden combinar usando operadores `y`, `o`, `no`.

## Tecnologías utilizadas y pruebas

- Java 17 y Maven
- JUnit 5 (pruebas unitarias)
- JaCoCo: configurado para exigir un mínimo del 70 % de cobertura de líneas exclusivamente en el paquete `domain`

El `pom.xml` está dentro de `cea-emisoras/`, así que hay que entrar a esa carpeta antes de compilar. Para compilar el proyecto, ejecutar los tests y verificar la cobertura, ejecuta:

```powershell
cd cea-emisoras
mvn clean verify
```

## Trabajo colaborativo

Este módulo fue desarrollado como parte del Protocolo Colaborativo en el repositorio `protocolo-ddd-hexagonal`, utilizando ramas cortas, commits atómicos y Pull Requests revisados y aprobados mutuamente por los integrantes del equipo.