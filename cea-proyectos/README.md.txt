# CEA Proyectos - Arquitectura Hexagonal + DDD

Este proyecto implementa el caso de estudio de gestión de proyectos de una empresa, utilizando los principios de Domain-Driven Design (DDD) y Arquitectura Hexagonal.

## Estructura del Proyecto

El código está organizado en tres capas principales, respetando la regla de dependencia (hacia adentro):

- domain: El núcleo del negocio. Es Java puro, sin dependencias de frameworks ni bases de datos.
  - entity: Agregados y entidades (ProjectModel, TaskModel, EmployeeModel).
  - valueobject: Objetos de valor inmutables (ProjectId, EmployeeId, ProjectDateRange, etc.).
  - event: Eventos de dominio.
  - exception: Excepciones personalizadas del negocio.
  - policy: Patrón Strategy para reglas de asignación.
  - service: Servicios de dominio (ProjectFactory, TaskAssignmentService).
- application: Orquesta los casos de uso. Define qué puede hacer el sistema y qué necesita del exterior.
  - dto: Objetos de transferencia de datos (Commands).
  - port.in: Interfaces de los casos de uso.
  - port.out: Interfaces de los repositorios y servicios externos.
  - service: Implementación de los casos de uso.
- infrastructure: Detalles técnicos (bases de datos, controladores, etc.).
  - controller: Adaptador de entrada que simula la interfaz de usuario.
  - repository: Adaptador de salida (InMemoryProjectRepository) que implementa el puerto de persistencia.

## Patrones aplicados
- Factory: ProjectFactory para crear proyectos válidos.
- Strategy: AssignmentConflictPolicy para resolver conflictos de asignación.
- Repository: ProjectRepositoryPort para abstraer el almacenamiento.

## Cómo ejecutar

Para compilar el proyecto y ejecutar las pruebas unitarias:
mvn clean test

Para ejecutar la aplicación de demostración:
mvn exec:java -Dexec.mainClass="com.neroandnero.cea.proyectos.Application"