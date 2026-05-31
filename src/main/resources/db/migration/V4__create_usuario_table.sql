-- V4: Create usuario table and seed admin user

CREATE TABLE clinica.usuario (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v7(),
    persona_id UUID NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_usuario_persona FOREIGN KEY (persona_id) REFERENCES clinica.persona(id),
    CONSTRAINT uk_usuario_email UNIQUE (email)
);

CREATE INDEX idx_usuario_email ON clinica.usuario(email);
CREATE INDEX idx_usuario_persona_id ON clinica.usuario(persona_id);

CREATE TRIGGER update_usuario_updated_at
    BEFORE UPDATE ON clinica.usuario
    FOR EACH ROW
    EXECUTE FUNCTION clinica.set_updated_at();

-- Seed: Create admin odontologist persona
INSERT INTO clinica.persona (
    id,
    tipo_documento_id,
    numero_documento,
    nombres,
    apellido_paterno,
    apellido_materno,
    sexo_id,
    fecha_nacimiento,
    created_at,
    updated_at
) VALUES (
    '00000000-0000-0000-0000-000000000001',
    1,
    '00000000',
    'Admin',
    'Sistema',
    'Inspire',
    1,
    '1990-01-01',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Seed: Create admin user (password: admin123)
-- BCrypt hash for "admin123"
INSERT INTO clinica.usuario (
    id,
    persona_id,
    email,
    password_hash,
    is_active,
    created_at,
    updated_at
) VALUES (
    '00000000-0000-0000-0000-000000000002',
    '00000000-0000-0000-0000-000000000001',
    'admin@inspire.com',
    '$2a$10$g./ETz0vGb0z5d1j5WElbOF5bb3igGNIQt.Nm69UnS3WJQhHGEsj6',
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
