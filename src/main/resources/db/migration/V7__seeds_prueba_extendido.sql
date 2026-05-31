-- =====================================================================
-- V7: Seeds de datos de prueba extendidos
-- Agrega 2 pacientes adicionales, citas, atenciones y odontogramas
-- para visualización completa en frontend
-- =====================================================================

SET search_path TO clinica;

-- =====================================================================
-- PACIENTE 2: Roberto Sánchez Torres
-- =====================================================================
INSERT INTO persona (
    id, tipo_documento_id, numero_documento, nombres,
    apellido_paterno, apellido_materno, sexo_id, fecha_nacimiento,
    celular, email, direccion, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000020',
    1, '45678901', 'Roberto Carlos',
    'Sánchez', 'Torres', 1,
    '1978-03-22',
    '988765432', 'roberto.sanchez@email.com', 'Jr. Amazonas 123, Arequipa',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

INSERT INTO paciente (
    id, persona_id, codigo_historia, lugar_nacimiento, procedencia,
    viajes_ultimo_anio, grado_instruccion_id, ocupacion, estado_civil_id,
    is_active, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000021',
    '20000000-0000-0000-0000-000000000020',
    'HC-2025-0002', 'Arequipa', 'Arequipa',
    'Si', 6, 'Ingeniero', 1,
    TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

INSERT INTO historia_clinica (
    id, paciente_id, fecha_apertura, odontologo_id,
    observaciones_generales, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000022',
    '20000000-0000-0000-0000-000000000021',
    '2025-02-10',
    '00000000-0000-0000-0000-000000000001',
    'Paciente refiere bruxismo. Primera consulta para evaluación general.',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

INSERT INTO contacto_emergencia (
    id, paciente_id, nombres_completos, celular, parentesco_id,
    is_apoderado, prioridad, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000023',
    '20000000-0000-0000-0000-000000000021',
    'Ana Torres de Sánchez', '987654321', 2,
    TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- PACIENTE 3: Lucía Fernández Vargas
-- =====================================================================
INSERT INTO persona (
    id, tipo_documento_id, numero_documento, nombres,
    apellido_paterno, apellido_materno, sexo_id, fecha_nacimiento,
    celular, email, direccion, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000024',
    1, '78901234', 'Lucía Mercedes',
    'Fernández', 'Vargas', 2,
    '1992-11-08',
    '912345678', 'lucia.fernandez@email.com', 'Av. Brasil 789, Lima',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

INSERT INTO paciente (
    id, persona_id, codigo_historia, lugar_nacimiento, procedencia,
    viajes_ultimo_anio, grado_instruccion_id, ocupacion, estado_civil_id,
    is_active, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000025',
    '20000000-0000-0000-0000-000000000024',
    'HC-2025-0003', 'Lima', 'Lima',
    'No', 5, 'Abogada', 2,
    TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

INSERT INTO historia_clinica (
    id, paciente_id, fecha_apertura, odontologo_id,
    observaciones_generales, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000026',
    '20000000-0000-0000-0000-000000000025',
    '2025-03-01',
    '00000000-0000-0000-0000-000000000001',
    'Paciente nueva. Embarazo de 5 meses. Sin antecedentes odontológicos previos.',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

INSERT INTO contacto_emergencia (
    id, paciente_id, nombres_completos, celular, parentesco_id,
    is_apoderado, prioridad, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000027',
    '20000000-0000-0000-0000-000000000025',
    'Miguel Fernández', '912345679', 1,
    TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- CITAS PARA PACIENTE 2 (Roberto)
-- =====================================================================
-- Cita 2: ATENDIDA
INSERT INTO cita (
    id, paciente_id, odontologo_id, fecha_hora_inicio, fecha_hora_fin,
    estado, motivo, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000030',
    '20000000-0000-0000-0000-000000000021',
    '00000000-0000-0000-0000-000000000001',
    '2025-02-10 10:00:00-05', '2025-02-10 11:30:00-05',
    'ATENDIDA', 'Evaluación de bruxismo y sonrisa gingival.',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- Cita 3: PROGRAMADA (futura)
INSERT INTO cita (
    id, paciente_id, odontologo_id, fecha_hora_inicio, fecha_hora_fin,
    estado, motivo, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000031',
    '20000000-0000-0000-0000-000000000021',
    '00000000-0000-0000-0000-000000000001',
    '2026-06-15 09:00:00-05', '2026-06-15 10:00:00-05',
    'PROGRAMADA', 'Control y seguimiento de bruxismo.',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- CITAS PARA PACIENTE 3 (Lucía)
-- =====================================================================
-- Cita 4: CONFIRMADA
INSERT INTO cita (
    id, paciente_id, odontologo_id, fecha_hora_inicio, fecha_hora_fin,
    estado, motivo, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000032',
    '20000000-0000-0000-0000-000000000025',
    '00000000-0000-0000-0000-000000000001',
    '2026-06-02 14:00:00-05', '2026-06-02 15:00:00-05',
    'CONFIRMADA', 'Limpieza dental general.',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- Cita 5: CANCELADA
INSERT INTO cita (
    id, paciente_id, odontologo_id, fecha_hora_inicio, fecha_hora_fin,
    estado, motivo, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000033',
    '20000000-0000-0000-0000-000000000025',
    '00000000-0000-0000-0000-000000000001',
    '2026-05-20 11:00:00-05', '2026-05-20 12:00:00-05',
    'CANCELADA', 'Primera cita de evaluación.',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- ATENCIONES PARA PACIENTE 2 (Roberto) - ATENDIDA
-- =====================================================================
INSERT INTO atencion (
    id, cita_id, historia_clinica_id, odontologo_id,
    fecha_inicio, fecha_fin, notas, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000040',
    '20000000-0000-0000-0000-000000000030',
    '20000000-0000-0000-0000-000000000022',
    '00000000-0000-0000-0000-000000000001',
    '2025-02-10 10:05:00-05', '2025-02-10 11:25:00-05',
    'Se diagnosticó bruxismo leve. Se recommendó uso de férula de descarga nocturna. Sonrisa gingival leve, no requiere tratamiento invasivo por el momento.',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- =====================================================================
-- ODONTOGRAMA PARA PACIENTE 2 (Roberto)
-- =====================================================================
INSERT INTO odontograma (
    id, historia_clinica_id, atencion_id, fecha,
    is_inicial, observaciones, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000041',
    '20000000-0000-0000-0000-000000000022',
    '20000000-0000-0000-0000-000000000040',
    '2025-02-10 10:30:00-05',
    TRUE,
    'Odontograma inicial. Desgaste oclusal leve por bruxismo en piezas 16, 26, 36, 46. Encías saludables.',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- Hallazgos del odontograma de Roberto (desgaste por bruxismo)
INSERT INTO odontograma_hallazgo (
    id, odontograma_id, pieza_id, cara_id, condicion_id,
    estado, notas, is_registrado_voz, transcripcion_voz,
    created_at, updated_at
) VALUES
    -- Pieza 16: Desgaste oclusal
    (
        '20000000-0000-0000-0000-000000000042',
        '20000000-0000-0000-0000-000000000041',
        16, 6, 12,
        'EXISTENTE', 'Desgaste oclusal leve. Se recomienda férula.',
        FALSE, '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    ),
    -- Pieza 26: Desgaste oclusal
    (
        '20000000-0000-0000-0000-000000000043',
        '20000000-0000-0000-0000-000000000041',
        26, 6, 12,
        'EXISTENTE', 'Desgaste oclusal leve. Mismo tratamiento que 16.',
        FALSE, '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    ),
    -- Pieza 36: Desgaste oclusal
    (
        '20000000-0000-0000-0000-000000000044',
        '20000000-0000-0000-0000-000000000041',
        36, 6, 12,
        'EXISTENTE', 'Desgaste oclusal moderado inferior.',
        FALSE, '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    ),
    -- Pieza 46: Desgaste oclusal
    (
        '20000000-0000-0000-0000-000000000045',
        '20000000-0000-0000-0000-000000000041',
        46, 6, 12,
        'EXISTENTE', 'Desgaste oclusal moderado inferior.',
        FALSE, '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    );

-- =====================================================================
-- PLAN DE TRATAMIENTO PARA PACIENTE 2 (Roberto)
-- =====================================================================
INSERT INTO plan_tratamiento (
    id, atencion_id, historia_clinica_id, estado, monto_total,
    observaciones, is_documento_impreso, created_at, updated_at
) VALUES (
    '20000000-0000-0000-0000-000000000046',
    '20000000-0000-0000-0000-000000000040',
    '20000000-0000-0000-0000-000000000022',
    'EN_EJECUCION', 450.00,
    'Plan para manejo de bruxismo: férula de descarga + control.',
    FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

INSERT INTO plan_tratamiento_detalle (
    id, plan_tratamiento_id, procedimiento_id, pieza_id, cara_id,
    cantidad, precio_unitario, estado, orden, notas,
    created_at, updated_at
) VALUES
    (
        '20000000-0000-0000-0000-000000000047',
        '20000000-0000-0000-0000-000000000046',
        (SELECT id FROM procedimiento WHERE codigo = 'CONTROL_ORTO'),
        0, 8, 1, 450.00, 'EJECUTADO', 1,
        'Férula de descarga superior personalizada.',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    );

INSERT INTO plan_tratamiento_evento (
    id, plan_tratamiento_id, estado_nuevo, actor_persona_id,
    aceptado_por, notas, created_at
) VALUES
    (
        '20000000-0000-0000-0000-000000000048',
        '20000000-0000-0000-0000-000000000046',
        'PROPUESTO',
        '00000000-0000-0000-0000-000000000001',
        '', 'Plan propuesto para bruxismo.',
        CURRENT_TIMESTAMP
    ),
    (
        '20000000-0000-0000-0000-000000000049',
        '20000000-0000-0000-0000-000000000046',
        'ACEPTADO',
        '00000000-0000-0000-0000-000000000001',
        'Roberto Carlos Sánchez Torres', 'Paciente acepta tratamiento.',
        CURRENT_TIMESTAMP
    ),
    (
        '20000000-0000-0000-0000-000000000050',
        '20000000-0000-0000-0000-000000000046',
        'EN_EJECUCION',
        '00000000-0000-0000-0000-000000000001',
        '', 'Férula en proceso de fabricación.',
        CURRENT_TIMESTAMP
    );

-- =====================================================================
-- AHORA ACTUALIZAR LA HISTORIA_CLINICA_ID EN PACIENTES
-- (V6 agregó esta columna, necesita actualizarse)
-- =====================================================================
UPDATE paciente SET historia_clinica_id = '20000000-0000-0000-0000-000000000003' WHERE id = '20000000-0000-0000-0000-000000000002';
UPDATE paciente SET historia_clinica_id = '20000000-0000-0000-0000-000000000022' WHERE id = '20000000-0000-0000-0000-000000000021';
UPDATE paciente SET historia_clinica_id = '20000000-0000-0000-0000-000000000026' WHERE id = '20000000-0000-0000-0000-000000000025';
