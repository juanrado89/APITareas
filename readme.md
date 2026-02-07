API REST desarrollada con Spring Boot para gestionar tareas. Cada tarea tiene id, título, descripción y estado (PENDIENTE o COMPLETADA). El estado se modela como una entidad independiente relacionada por id, lo que permite añadir nuevos estados en el futuro sin modificar la estructura principal. La API permite crear, listar, actualizar y eliminar tareas usando persistencia en base de datos PostgreSQL.

Cómo ejecutar el proyecto

Requisitos:
Java 17 o superior
Maven (o usar el wrapper incluido mvnw / mvnw.cmd)
Acceso a la base de datos PostgreSQL configurada

Configurar la base de datos

Editar el archivo src/main/resources/application.properties con los datos de conexión:

spring.datasource.url=jdbc:postgresql://HOST:PUERTO/BD
spring.datasource.username=USUARIO
spring.datasource.password=PASSWORD
spring.jpa.hibernate.ddl-auto=update

Ejecución

Desde la raíz del proyecto:

Windows:
.\mvnw spring-boot:run

Linux o Mac:
./mvnw spring-boot:run

Ejecución de tests

Windows:
.\mvnw test

Linux o Mac:
./mvnw test

Endpoints principales

POST /tarea — crear tarea
GET /tarea — listar tareas
PUT /tarea/{id} — actualizar tarea
PATCH /tarea/{id}/estado — actualizar solo el estado
DELETE /tarea/{id} — eliminar tarea

Herramientas y recursos utilizados

Spring Boot para crear la API REST y la configuración automática del proyecto.
Spring Data JPA para la capa de persistencia y acceso a base de datos mediante repositorios.
PostgreSQL como base de datos relacional para almacenamiento persistente.
MapStruct para mapear entidades y DTOs automáticamente.
Lombok para reducir código repetitivo como getters, setters y constructores.
JUnit y Mockito para crear tests unitarios del servicio simulando dependencias.
Swagger (springdoc-openapi) para probar y documentar los endpoints durante el desarrollo.