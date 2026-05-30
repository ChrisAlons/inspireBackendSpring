# AGENTS.md

> Full context: see `CLAUDE.md` for complete conventions, structure, and rules.

## Commands

```bash
# Windows
mvnw.cmd spring-boot:run          # run (auto-starts Postgres via compose.yaml)
mvnw.cmd clean package            # compile
mvnw.cmd test                     # tests (none written yet)

# Linux/macOS
./mvnw spring-boot:run
./mvnw clean package
./mvnw test
```

No lint or typecheck commands — compilation (`clean package`) is the only code-quality gate.

## Architecture

- **Java 25** + **Spring Boot 4.0.6** + **Hibernate 7** + **PostgreSQL 18** (compose.yaml)
- All tables live in schema `clinica` (not `public`). Hibernate config: `default_schema=clinica`.
- `ddl-auto=validate` — schema is controlled exclusively by Flyway. Never change this.
- Spring Boot Docker Compose auto-starts Postgres on `spring-boot:run`. No manual `docker compose up` needed.
- Env vars in `.env.example` — copy to `.env` to override defaults (DB name, user, port).

## Critical Conventions

- **Soft delete everywhere**: all transactional entities use `@SQLDelete` + `@SQLRestriction("deleted_at IS NULL")`. Do NOT add `AndDeletedAtIsNull` to repository methods — the restriction handles it.
- **Catalogs by business code**: DTOs reference catalogs as `"DNI"`, `"CARIES"`, `"CASADO"` — never by numeric ID. Service resolves via `findByCodigo()`.
- **PostgreSQL native enums**: require `@Enumerated(EnumType.STRING)` + `@JdbcTypeCode(SqlTypes.NAMED_ENUM)` + `columnDefinition = "enum_name"`. See `Cita.java` for the pattern.
- **Sentinel values**: `pieza_dental.id=0` and `cara_dental.codigo='NO_APLICA'` are valid, not errors.
- **No MapStruct**: mappers are package-private classes with static methods.
- **DTOs are Java records**: `Create*Dto` (validated), `Update*Dto` (all optional), `*Response` (flat).
- **Never expose JPA entities** in controllers — always DTOs.

## Module Status

All modules have full stack (Service + Controller + Mapper + DTOs) implemented:

- `persona` — CRUD standalone (odontólogos, referencias)
- `paciente` — CRUD con Persona embebida (módulo de referencia)
- `contactoemergencia` — CRUD anidado bajo paciente
- `historiaclinica` — 1:1 con paciente
- `cita` — CRUD + máquina de estados (PROGRAMADA → CONFIRMADA → EN_CURSO → ATENDIDA/CANCELADA/NO_ASISTIO)
- `atencion` — CRUD + cerrar (fechaFin) + listar en curso
- `enfermedadactual` — 1:1 con atencion
- `antecedente` — personales + familiares, ligados a historiaclinica
- `habito` — unique constraint por (historiaClinicaId, tipo)
- `odontograma` — CRUD + hallazgos + **endpoint de voz** (`POST /{id}/hallazgos/voz`)
- `plantratamiento` — cabecera + detalles + eventos, flujo de estados (PROPUESTO → ACEPTADO → EN_EJECUCION → COMPLETADO/PARCIAL)
- `tratamientoejecutado` — registro de procedimientos completados, marca detalle como EJECUTADO

## Flyway Migrations

- Files: `src/main/resources/db/migration/V<n>__<name>.sql`
- V1 = schema, V2 = catalog seeds. Next migration = V3.
- Never modify applied migrations. Changes go in a new migration file.
- Seeds go in separate migrations, not in schema migrations.

## Testing & API

- **No tests written yet** — test infrastructure exists (`spring-boot-starter-*-test` in pom.xml) but no test cases.
- **Bruno** collections in `bruno/` for manual API testing.
- **OpenAPI/Swagger UI** available via `springdoc-openapi` (dependency in pom.xml).

## Gotchas

- `procedimiento` is a catalog-like table but uses UUID PK + soft delete (unlike other catalogs which use SMALLINT + `is_active`).
- `plan_tratamiento_evento` has no soft delete and no `updated_at` — it's an append-only audit log.
- `habito` has a unique constraint on `(historia_clinica_id, tipo)` — one record per habit type per history.
- `atencion.fecha_fin` is intentionally nullable (NULL = in progress). One of only two allowed NULL fields (the other being `deleted_at`).
