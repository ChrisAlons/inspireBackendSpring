# CLAUDE.md - Backend Clínica Odontológica (SaaS para odontólogos)

Este archivo provee contexto a Claude Code para asistir en el desarrollo del backend.

## Contexto del proyecto

Sistema de gestión odontológica para odontólogos particulares. **MVP actual: monousuario** (un solo doctor). Multi-tenancy SaaS se agregará después de validar el producto.

Funcionalidades principales:
- Registro de pacientes con historia clínica completa
- Agendamiento de citas y registro de atenciones
- Odontograma con captura de hallazgos por **comandos de voz** (transcripción se persiste en `transcripcion_voz`)
- Plan de tratamiento con flujo: PROPUESTO → ACEPTADO (firmado físicamente, no digital) → EN_EJECUCION → COMPLETADO/PARCIAL
- Ejecución parcial de tratamientos (paciente puede agendar para luego)

## Stack técnico

- **Java 25** (records, pattern matching, virtual threads disponibles)
- **Spring Boot 4.0.6**: Web MVC, Data JPA, Validation, Flyway
- **Spring Framework 7** (incluido por Spring Boot 4.0.6)
- **Hibernate 7** (incluido por Spring Boot 4.0.6) con `@UuidGenerator(style = TIME)` para UUID v7 nativo
- **PostgreSQL 15+** con extensiones `pgcrypto`, función `uuid_generate_v7()` custom en schema `clinica`
- **Flyway** para migraciones (NO usar `ddl-auto: update`)
- **Lombok** para reducir boilerplate
- **Jakarta Validation** para DTOs
- **Spring Boot DevTools** (hot reload en desarrollo)
- **Spring Boot Docker Compose** (levanta `compose.yaml` automáticamente)

### GroupId / Package base

- groupId Maven: `org.inspire`
- Package raíz Java: `org.inspire.backend`
- Schema PostgreSQL: `clinica`

### Dependencias en pom.xml

| Starter / Librería | Scope | Notas |
|---|---|---|
| `spring-boot-starter-data-jpa` | compile | Hibernate 7 |
| `spring-boot-starter-flyway` + `flyway-database-postgresql` | compile | migraciones |
| `spring-boot-starter-webmvc` | compile | controladores REST |
| `spring-boot-starter-validation` | compile | Jakarta Validation en DTOs |
| `spring-boot-devtools` | runtime/optional | hot reload |
| `spring-boot-docker-compose` | runtime/optional | levanta compose.yaml |
| `postgresql` | runtime | driver JDBC |
| `lombok` | optional | reduce boilerplate |

## Estructura del proyecto (package-by-feature)

```
src/main/java/org/inspire/backend/
├── InspireBackendApplication.java
├── common/
│   ├── BaseEntity.java                  # UUID v7 + audit + soft delete
│   ├── enums/                           # EstadoCita, EstadoPlan, EstadoHallazgo, etc.
│   └── exception/
│       ├── BusinessException.java       # 422 - regla de negocio
│       ├── ConflictException.java       # 409 - duplicados
│       ├── ResourceNotFoundException.java  # 404
│       └── GlobalExceptionHandler.java
├── catalogo/                            # Tablas de referencia (SMALLINT IDs)
│   └── <nombre>/
│       ├── <Nombre>.java                # Entity
│       └── <Nombre>Repository.java
└── modules/                             # Tablas transaccionales (UUID v7 IDs)
    └── <nombre>/
        ├── <Nombre>.java                # Entity
        ├── <Nombre>Repository.java
        ├── <Nombre>Service.java
        ├── <Nombre>Controller.java
        ├── <Nombre>Mapper.java          # package-private
        └── dto/
            ├── Create<Nombre>Dto.java   # record con @Valid
            ├── Update<Nombre>Dto.java   # record con campos opcionales
            └── <Nombre>Response.java    # record con datos planos
```

## Convenciones obligatorias

### Identificadores

- **Catálogos** (sexo, parentesco, cara_dental, etc.): `SMALLINT` con código de negocio único (ej. `'DNI'`, `'OCLUSAL'`, `'CARIES'`). Pequeños, estables, lookups frecuentes.
- **Tablas transaccionales**: `UUID v7` generado por Hibernate con `@UuidGenerator(style = TIME)`. Nunca BIGINT en transaccionales (esto será un SaaS multi-tenant en el futuro).
- **Pieza dental**: PK natural con FDI (11..48 adulto, 51..85 deciduo). El id `0` es sentinela "NO_APLICA" para procedimientos sin pieza específica.

### Soft delete

Todas las entidades transaccionales tienen `deleted_at TIMESTAMPTZ`. Implementación con Hibernate:

```java
@SQLDelete(sql = "UPDATE clinica.<tabla> SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class MiEntidad extends BaseEntity { ... }
```

`BaseEntity` ya define `id`, `createdAt`, `updatedAt`, `deletedAt` con `@PrePersist` y `@PreUpdate`.

Catálogos NO usan soft delete; usan `is_active BOOLEAN`.

### Reducción de NULLs

Los nullables se evitan así:
- **Texto opcional**: `NOT NULL DEFAULT ''` en BD, `String campo = ""` en entidad. Las queries usan `=` directo, sin `COALESCE`.
- **FKs opcionales**: usar registro sentinela (ej. `cara_dental.codigo='NO_APLICA'`, `pieza_dental.id=0`) en lugar de NULL. Permite joins uniformes.
- **NULLs intencionales permitidos**: solo `deleted_at` (NULL = activo) y `atencion.fecha_fin` (NULL = en curso). Ambos con índices parciales `WHERE deleted_at IS NULL`.

### Enums PostgreSQL

Los enums Java mapean a tipos `ENUM` nativos de PostgreSQL:

```java
@Enumerated(EnumType.STRING)
@JdbcTypeCode(SqlTypes.NAMED_ENUM)
@Column(nullable = false, columnDefinition = "estado_cita")
private EstadoCita estado = EstadoCita.PROGRAMADA;
```

Enums actuales: `EstadoCita`, `EstadoHallazgo`, `EstadoPlan`, `EstadoDetallePlan`, `TipoHabito`, `FrecuenciaHabito`. Estos son **estados de máquina cerrada** (poco probable que cambien). Si necesitas extensibilidad operativa (agregar valores sin redeploy), usa una tabla de catálogo en su lugar.

### Fechas y zonas horarias

- Siempre `TIMESTAMPTZ` en BD, `OffsetDateTime` en Java.
- Spring config: `spring.jackson.time-zone: UTC` y `hibernate.jdbc.time_zone: UTC`.
- Fechas sin hora: `DATE` / `LocalDate`.

### DTOs y validación

- DTOs son **records de Java 25**.
- `Create*Dto`: campos requeridos con `@NotBlank`, `@NotNull`, `@Past`, `@Email`, `@Size`. Anidar `@Valid` para listas.
- `Update*Dto`: todos los campos opcionales (sin `@NotBlank`); el service aplica solo los no-null.
- `*Response`: datos planos sin entidades JPA.
- Las referencias a catálogos en DTOs usan **código de negocio** (`"DNI"`, `"CASADO"`, `"OCLUSAL"`), no IDs. El service los resuelve via `findByCodigo()`.

### Controllers

```java
@RestController
@RequestMapping("/api/<recurso>")
@RequiredArgsConstructor
public class XxxController {
    private final XxxService service;

    @PostMapping
    public ResponseEntity<XxxResponse> crear(@Valid @RequestBody CreateXxxDto dto) {
        XxxResponse creado = service.crear(dto);
        return ResponseEntity
            .created(URI.create("/api/<recurso>/" + creado.id()))
            .body(creado);
    }
    // GET, PUT, DELETE siguen el mismo patrón
}
```

- Endpoints REST estándar: `POST /` (201), `GET /{id}` (200), `GET /` paginado (200), `PUT /{id}` (200), `DELETE /{id}` (204).
- Búsqueda con `@RequestParam(required = false) String q` + `Pageable`.
- Nunca devolver entidades, siempre DTOs.

### Services

- Anotados `@Service @RequiredArgsConstructor`.
- Métodos de escritura con `@Transactional`, lectura con `@Transactional(readOnly = true)`.
- Lanzar excepciones tipadas: `ResourceNotFoundException` (404), `ConflictException` (409), `BusinessException` (422).
- Validar catálogos por código y dar mensajes claros: `"Tipo de documento inválido: " + codigo`.
- Mappers son clases package-private con métodos estáticos. **No usar MapStruct** por ahora (mantener el proyecto simple para el MVP).

### Repositories

- Solo `extends JpaRepository<Entity, UUID>` (o `Short` para catálogos).
- Queries con derived method names cuando sea posible: `findByCodigoHistoria`, `existsByPersonaIdAndDeletedAtIsNull`.
- `@Query` solo cuando los derived names sean impracticables.
- **No crear `RepositoryImpl`**; lógica compleja va en Service.
- `@SQLRestriction` ya filtra `deleted_at IS NULL` automáticamente; no necesitas agregar `AndDeletedAtIsNull` a los métodos.

### Migraciones Flyway

- Archivos en `src/main/resources/db/migration/`.
- Convención: `V<n>__<descripcion_snake_case>.sql`.
- **NUNCA modificar migraciones ya aplicadas**. Cambios = nueva migración.
- V1 = schema inicial (tablas, tipos, funciones, triggers). V2 = seeds de catálogos. V3+ = cambios incrementales.
- Validar con: `psql -d clinica -c "SELECT version, success FROM clinica.flyway_schema_history;"`.

## Reglas de oro

1. **No usar `ddl-auto: update` ni `create`**. Solo `validate`. El schema lo controla Flyway.
2. **Nunca exponer entidades JPA** en controllers. Siempre DTOs.
3. **Soft delete por defecto**. Solo eliminar físicamente datos verdaderamente desechables (logs viejos, eventos antiguos vía batch).
4. **Catálogos por código, no por ID**. En APIs y DTOs siempre referenciarlos como `"DNI"`, `"CARIES"`, etc.
5. **Transacciones explícitas**. Todo método de service que escribe debe ser `@Transactional`.
6. **No insertar en migraciones de schema**. Seeds van en migraciones V2+ separadas.
7. **Pieza dental id=0 y cara_dental codigo='NO_APLICA'** son sentinelas válidos, no errores.
8. **El plan de tratamiento NO tiene firma digital**. Se imprime físicamente y el paciente firma a mano. Marcar `is_documento_impreso = true` cuando corresponda.

## Patrón a replicar para nuevos módulos

Solo el módulo `paciente/` tiene flujo completo (Service + Controller + Mapper + DTOs) como referencia. Los demás módulos tienen Entity + Repository ya listos pero falta:

1. **Cita**: CRUD + transiciones de estado (PROGRAMADA → CONFIRMADA → EN_CURSO → ATENDIDA/CANCELADA).
2. **Atención**: se crea al iniciar cita; cierra con `fecha_fin`. 1:1 con cita.
3. **EnfermedadActual**: 1:1 con atención.
4. **Antecedentes (personales/familiares) + Hábitos**: ligados a HistoriaClinica.
5. **Odontograma + Hallazgos**: incluir endpoint `POST /api/odontogramas/{id}/hallazgos/voz` que reciba transcripción y la persista en `transcripcion_voz` con `is_registrado_voz = true`.
6. **Plan de Tratamiento**: cabecera + detalles + eventos. Service debe registrar evento en cada cambio de estado.
7. **Tratamiento Ejecutado**: registro de procedimientos completados, actualiza el estado del detalle correspondiente.

Cuando agregue un módulo nuevo, seguir EXACTAMENTE el patrón de `paciente/`: mismas anotaciones, misma estructura de DTOs, mismos códigos HTTP.

## Comandos útiles

```bash
# Compilar y correr
./mvnw spring-boot:run

# Solo compilar
./mvnw clean package

# Tests
./mvnw test

# Migraciones aplicadas
psql -d clinica -c "SELECT version, description, success FROM clinica.flyway_schema_history;"

# Ver schema
psql -d clinica -c "\dt clinica.*"
```

## Pendientes técnicos a futuro (no para MVP)

- **Multi-tenancy SaaS**: agregar tabla `tenant`, columna `tenant_id` en transaccionales, RLS en PostgreSQL.
- **Auth**: Spring Security + JWT, `actor_persona_id` en eventos debe venir del `SecurityContext`.
- **Comandos de voz**: integración con servicio STT + parser de transcripciones.
- **Generación de PDF** del plan de tratamiento para impresión.
- **Tabla `persona_evento`** para auditar reactivaciones de cuenta.
- **Separar generación de `codigo_historia`** a servicio con secuencia DB para evitar race conditions.
