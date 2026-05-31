# AGENTS.md

> Contexto completo: ver `CLAUDE.md` para convenciones, estructura y reglas.

## Visión General del Proyecto

SaaS de gestión odontológica para odontólogos particulares. MVP monousuario (un solo doctor por instancia). Multi-tenancy se agregará después de validar el producto.

**Stack tecnológico:**
- Backend: Java 25 + Spring Boot 4.0.6 + Hibernate 7 + PostgreSQL 18
- Frontend: Angular 21 + Tailwind v4
- Auth: Spring Security + JWT

## Comandos

```bash
# Backend
cd ../inspireBackendSpring-main
./mvnw.cmd spring-boot:run          # ejecutar (levanta Postgres automáticamente)
./mvnw.cmd clean package            # compilar
./mvnw.cmd test                     # tests (ninguno escrito aún)

# Frontend
cd ../inspireFrontendAngular
npm start                           # dev server en http://localhost:4200
npm run build                       # build de producción
```

## Arquitectura

- Todas las tablas viven en schema `clinica` (no `public`)
- `ddl-auto=validate` — schema controlado exclusivamente por Flyway
- Spring Boot Docker Compose levanta Postgres automáticamente con `spring-boot:run`
- Vars de entorno en `.env.example` — copiar a `.env` para sobreescribir

## Convenciones Críticas

- **Soft delete en todas partes**: entidades usan `@SQLDelete` + `@SQLRestriction("deleted_at IS NULL")`
- **Catálogos por código de negocio**: DTOs referencian catálogos como `"DNI"`, `"CARIES"`, `"CASADO"` — nunca por ID numérico
- **DTOs son records de Java**: `Create*Dto` (validado), `Update*Dto` (todos opcionales), `*Response` (plano)
- **Sin MapStruct**: mappers son clases package-private con métodos estáticos
- **Nunca exponer entidades JPA** en controllers — siempre DTOs

## Estado de Módulos

### Backend (COMPLETO)
Todos los módulos tienen stack completo: Service + Controller + Mapper + DTOs

| Módulo | Estado | Notas |
|--------|--------|-------|
| `persona` | ✅ | CRUD independiente (odontólogos, referencias) |
| `paciente` | ✅ | CRUD con Persona embebida |
| `contactoemergencia` | ✅ | CRUD anidado bajo paciente |
| `historiaclinica` | ✅ | 1:1 con paciente |
| `cita` | ✅ | CRUD + máquina de estados + join con paciente |
| `atencion` | ✅ | CRUD + cerrar (fechaFin) + listar en curso |
| `enfermedadactual` | ✅ | 1:1 con atencion |
| `antecedente` | ✅ | personales + familiares, ligados a historiaclinica |
| `habito` | ✅ | unique constraint por (historiaClinicaId, tipo) |
| `odontograma` | ✅ | CRUD + hallazgos + endpoint de voz |
| `plantratamiento` | ✅ | cabecera + detalles + eventos |
| `tratamientoejecutado` | ✅ | registro de procedimientos completados |

### Frontend (PARCIAL)

| Módulo | Estado | Notas |
|--------|--------|-------|
| Login | ✅ | Auth JWT con token storage |
| Dashboard | ✅ | Layout básico |
| Pacientes | ✅ | List + Form + Detail components |
| Citas | ✅ | List + Form + Detail + cambiar estado |
| Atenciones | ✅ | List (en curso) + Detail + cerrar |
| Odontograma | ⏳ | No iniciado |
| Plan Tratamiento | ⏳ | No iniciado |

## Autenticación y Autorización

- **Spring Security + JWT**: todos los endpoints requieren `Authorization: Bearer <token>` excepto `/api/auth/**` y Swagger
- **Login**: `POST /api/auth/login` con `{"email", "password"}` retorna JWT + info del usuario
- **Admin seed**: `admin@inspire.com` / `admin123`
- **UsuarioPrincipal**: record con `usuarioId`, `personaId`, `email`. Usar `@AuthenticationPrincipal` en controllers
- **Multi-tenancy**: métodos `buscar()` filtran por `odontologoId` del `SecurityContext`

## Endpoints API Principales

### Auth
- `POST /api/auth/login` — login, retorna JWT

### Pacientes
- `GET /api/pacientes` — listar (paginado, filtrado por odontólogo)
- `GET /api/pacientes/{id}` — obtener por id
- `POST /api/pacientes` — crear
- `PUT /api/pacientes/{id}` — actualizar
- `DELETE /api/pacientes/{id}` — soft delete

### Citas
- `GET /api/citas` — listar (paginado, filtrado por odontólogo)
- `GET /api/citas/{id}` — obtener por id
- `POST /api/citas` — crear
- `PUT /api/citas/{id}` — actualizar
- `PATCH /api/citas/{id}/estado` — cambiar estado
- `DELETE /api/citas/{id}` — soft delete

### Atenciones
- `GET /api/atenciones/en-curso` — listar atenciones con fechaFin=null
- `GET /api/atenciones/{id}` — obtener por id
- `GET /api/atenciones/por-historia/{historiaClinicaId}` — listar por historia
- `POST /api/atenciones` — crear
- `PUT /api/atenciones/{id}` — actualizar (solo notas)
- `PATCH /api/atenciones/{id}/cerrar` — cerrar atencion
- `DELETE /api/atenciones/{id}` — soft delete

## Relaciones de Entidades Clave

```
Persona (odontólogo o persona de contacto)
  └── Usuario (credenciales, FK to Persona)
  └── Paciente (FK to Persona)
        └── HistoriaClinica (FK to Paciente, @OneToOne)
              └── Cita (FK to Paciente + Odontologo)
                    └── Atencion (FK to Cita + HistoriaClinica + Odontologo)
```

## Migraciones Flyway

| Archivo | Descripción |
|---------|-------------|
| V1 | Schema inicial (todas las tablas, tipos, funciones, triggers) |
| V2 | Seeds de catálogos (sexo, tipo_documento, estado_civil, etc.) |
| V3 | Seeds de procedimientos |
| V4 | Tabla usuario + seed admin |
| V5 | Datos de prueba (paciente completo) |
| V6 | Agregar historia_clinica_id a tabla paciente |

**Regla**: Nunca modificar migraciones ya aplicadas. Cambios van en una nueva migración.

## Componentes Frontend

### Componentes Compartidos
- `shared/components/paciente-select/` — selector de paciente con búsqueda

### Componentes de Features
Cada feature tiene componentes list, form y detail:
- `features/pacientes/paciente-list/`
- `features/pacientes/paciente-form/`
- `features/pacientes/patient-detail/`
- `features/citas/cita-list/`
- `features/citas/cita-form/`
- `features/citas/cita-detail/`
- `features/atenciones/atencion-list/`
- `features/atenciones/atencion-detail/`

## Manejo de Estado

Los servicios usan Angular Signals:
```typescript
private _data = signal<Type[]>([]);
readonly data = this._data.asReadonly();
```

Los controladores inyectan servicios y usan signals computados para estado derivado.

## Ambiente

- Backend: `http://localhost:8080`
- Frontend: `http://localhost:4200` (proxea al backend)
- Base de datos: PostgreSQL via Docker Compose
- Usuario test: `admin@inspire.com` / `admin123`
- Paciente test: María García López (DNI: 12345678)

## Gotchas

- `procedimiento` usa UUID PK + soft delete (a diferencia de catálogos que usan SMALLINT + `is_active`)
- `plan_tratamiento_evento` es log de auditoría solo append (no soft delete, no `updated_at`)
- `atencion.fecha_fin` es intencionalmente nullable (NULL = en curso)
- Siempre usar `@AuthenticationPrincipal UsuarioPrincipal` para obtener el usuario actual y filtrar