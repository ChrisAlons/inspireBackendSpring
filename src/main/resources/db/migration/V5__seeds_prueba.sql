-- =====================================================================
-- V5: Seed de datos de prueba para flujo completo
-- Paciente → Historia → Cita → Atención → Odontograma → Plan Tratamiento
-- =====================================================================

SET search_path TO clinica;

-- =====================================================================
-- 1. Persona del paciente (María García López)
-- =====================================================================
INSERT INTO persona (
    id, tipo_documento_id, numero_documento, nombres,
    apellido_paterno, apellido_materno, sexo_id, fecha_nacimiento,
    celular, email, direccion, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000001',
    1, '87654321', 'María Elena',
    'García', 'López', 2,
    '1985-06-15',
    '999888777', 'maria.garcia@email.com', 'Av. Los Álamos 456, Lima',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- 2. Paciente
-- =====================================================================
INSERT INTO paciente (
    id, persona_id, codigo_historia, lugar_nacimiento, procedencia,
    viajes_ultimo_anio, grado_instruccion_id, ocupacion, estado_civil_id,
    is_active, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000002',
    '20000000-0000-0000-0000-000000000001',
    'HC-2025-0001', 'Lima', 'Lima',
    'No', 5, 'Contadora', 2,
    TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- 3. Contacto de emergencia (esposo)
-- =====================================================================
INSERT INTO contacto_emergencia (
    id, paciente_id, nombres_completos, celular, parentesco_id,
    is_apoderado, prioridad, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000004',
    '20000000-0000-0000-0000-000000000002',
    'Carlos Mendoza Ruiz', '988777666', 5,
    TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- 4. Historia clínica
-- =====================================================================
INSERT INTO historia_clinica (
    id, paciente_id, fecha_apertura, odontologo_id,
    observaciones_generales, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000003',
    '20000000-0000-0000-0000-000000000002',
    '2025-01-15',
    '00000000-0000-0000-0000-000000000001',
    'Paciente nueva, primera visita general. Sin antecedentes médicos relevantes.',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- 5. Antecedentes personales
-- =====================================================================
INSERT INTO antecedente_personal (
    id, historia_clinica_id, descripcion, fecha_registro, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000005',
    '20000000-0000-0000-0000-000000000003',
    'Alergia a penicilina diagnosticada en 2010. Sin otras alergias conocidas.',
    '2025-01-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- 6. Antecedentes familiares
-- =====================================================================
INSERT INTO antecedente_familiar (
    id, historia_clinica_id, parentesco_id, descripcion, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000006',
    '20000000-0000-0000-0000-000000000003',
    1, 'Padre con diabetes tipo 2 controlada.',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- 7. Hábitos
-- =====================================================================
INSERT INTO habito (
    id, historia_clinica_id, tipo, frecuencia, detalle, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000007',
    '20000000-0000-0000-0000-000000000003',
    'TABACO', 'OCASIONAL', 'Fumadora ocasional, aproximadamente 2-3 cigarros por semana.',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- 8. Cita (estado ATENDIDA para permitir crear atención)
-- =====================================================================
INSERT INTO cita (
    id, paciente_id, odontologo_id, fecha_hora_inicio, fecha_hora_fin,
    estado, motivo, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000008',
    '20000000-0000-0000-0000-000000000002',
    '00000000-0000-0000-0000-000000000001',
    '2025-01-15 09:00:00-05', '2025-01-15 10:30:00-05',
    'ATENDIDA', 'Dolor en molar superior derecho y limpieza general.',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- 9. Atención (cerrada - fecha_fin establecida)
-- =====================================================================
INSERT INTO atencion (
    id, cita_id, historia_clinica_id, odontologo_id,
    fecha_inicio, fecha_fin, notas, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000009',
    '20000000-0000-0000-0000-000000000008',
    '20000000-0000-0000-0000-000000000003',
    '00000000-0000-0000-0000-000000000001',
    '2025-01-15 09:05:00-05', '2025-01-15 10:25:00-05',
    'Primera atención: se diagnosticó caries en pieza 16 y 26. Se realizó limpieza profética.',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- 10. Enfermedad actual (motivo de consulta)
-- =====================================================================
INSERT INTO enfermedad_actual (
    id, atencion_id, motivo_consulta, tiempo_enfermedad,
    signos_sintomas, funciones_biologicas, expectativas_tratamiento,
    created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000010',
    '20000000-0000-0000-0000-000000000009',
    'Dolor molesto en molar superior derecho desde hace aproximadamente 1 semana.',
    '1 semana',
    'Dolor agudo al masticar alimentos fríos, presión en pieza 16.',
    'Buen estado general, sin fiebre.',
    'Paciente desea aliviar el dolor y restaurar las piezas afectadas.',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- 11. Odontograma inicial con hallazgos
-- =====================================================================
INSERT INTO odontograma (
    id, historia_clinica_id, atencion_id, fecha,
    is_inicial, observaciones, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000011',
    '20000000-0000-0000-0000-000000000003',
    '20000000-0000-0000-0000-000000000009',
    '2025-01-15 09:30:00-05',
    TRUE,
    'Odontograma inicial. Se encontraron caries incipientes en 16 y 26.',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- 12. Hallazgos del odontograma
--    Pieza 16 (Primer molar superior derecho): CARIES en cara OCLUSAL
--    Pieza 26 (Segundo molar superior izquierdo): OBTURACION temporal
-- =====================================================================
INSERT INTO odontograma_hallazgo (
    id, odontograma_id, pieza_id, cara_id, condicion_id,
    estado, notas, is_registrado_voz, transcripcion_voz,
    created_at, updated_at
) VALUES
    (
        '20000000-0000-0000-0000-000000000012',
        '20000000-0000-0000-0000-000000000011',
        16, 6, 1,
        'EXISTENTE', 'Caries oclusal profunda, requiere tratamiento.',
        FALSE, '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    ),
    (
        '20000000-0000-0000-0000-000000000013',
        '20000000-0000-0000-0000-000000000011',
        26, 6, 3,
        'EXISTENTE', 'Obturación temporal presente, necesita recambio.',
        FALSE, '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    );

-- =====================================================================
-- 13. Plan de tratamiento (estado ACEPTADO)
-- =====================================================================
INSERT INTO plan_tratamiento (
    id, atencion_id, historia_clinica_id, estado, monto_total,
    observaciones, is_documento_impreso, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000014',
    '20000000-0000-0000-0000-000000000009',
    '20000000-0000-0000-0000-000000000003',
    'ACEPTADO', 650.00,
    'Plan de tratamiento acordado con la paciente. Incluye restauración y control.',
    FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- 14. Detalles del plan de tratamiento
-- =====================================================================
INSERT INTO plan_tratamiento_detalle (
    id, plan_tratamiento_id, procedimiento_id, pieza_id, cara_id,
    cantidad, precio_unitario, estado, orden, notas,
    created_at, updated_at
) VALUES
    -- Detalle 1: Obturación en pieza 16
    (
        '20000000-0000-0000-0000-000000000015',
        '20000000-0000-0000-0000-000000000014',
        (SELECT id FROM procedimiento WHERE codigo = 'OBTURACION'),
        16, 6, 1, 150.00, 'PENDIENTE', 1,
        'Restauración con resina compuesta cara oclusal.',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    ),
    -- Detalle 2: Corona en pieza 26 (después de endodoncia)
    (
        '20000000-0000-0000-0000-000000000016',
        '20000000-0000-0000-0000-000000000014',
        (SELECT id FROM procedimiento WHERE codigo = 'CORONA'),
        26, 8, 1, 600.00, 'PENDIENTE', 2,
        'Corona de porcelana después de endodoncia.',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    );

-- =====================================================================
-- 15. Eventos del plan (auditoría de cambios de estado)
-- =====================================================================
INSERT INTO plan_tratamiento_evento (
    id, plan_tratamiento_id, estado_nuevo, actor_persona_id,
    aceptado_por, notas, created_at
) VALUES (
    '20000000-0000-0000-0000-000000000017',
    '20000000-0000-0000-0000-000000000014',
    'PROPUESTO',
    '00000000-0000-0000-0000-000000000001',
    '', 'Plan propuesto tras evaluación.',
    CURRENT_TIMESTAMP
),
(
    '20000000-0000-0000-0000-000000000018',
    '20000000-0000-0000-0000-000000000014',
    'ACEPTADO',
    '00000000-0000-0000-0000-000000000001',
    'María Elena García López', 'Paciente acepta plan propuesto.',
    CURRENT_TIMESTAMP
);

-- =====================================================================
-- 16. Un tratamiento ejecutado (primera ejecución del plan)
-- =====================================================================
INSERT INTO tratamiento_ejecutado (
    id, plan_detalle_id, atencion_id, odontologo_id,
    fecha_ejecucion, observaciones, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000019',
    '20000000-0000-0000-0000-000000000015',
    '20000000-0000-0000-0000-000000000009',
    '00000000-0000-0000-0000-000000000001',
    '2025-01-15 09:45:00-05',
    'Se colocó aislamiento absoluto y se realizó excavación de caries. Restauración completada.',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- Marcar el detalle como EJECUTADO
UPDATE plan_tratamiento_detalle
SET estado = 'EJECUTADO', updated_at = CURRENT_TIMESTAMP
WHERE id = '20000000-0000-0000-0000-000000000015';
