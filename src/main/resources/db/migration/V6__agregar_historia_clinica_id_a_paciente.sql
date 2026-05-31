-- =====================================================================
-- V6: Agregar columna historia_clinica_id a paciente
-- Para soportar relación @OneToOne de Paciente → HistoriaClinica
-- =====================================================================

SET search_path TO clinica;

ALTER TABLE paciente
ADD COLUMN historia_clinica_id UUID;

ALTER TABLE paciente
ADD CONSTRAINT fk_paciente_historia
    FOREIGN KEY (historia_clinica_id)
    REFERENCES historia_clinica(id);

CREATE INDEX idx_paciente_historia_clinica
    ON paciente (historia_clinica_id)
    WHERE historia_clinica_id IS NOT NULL;
