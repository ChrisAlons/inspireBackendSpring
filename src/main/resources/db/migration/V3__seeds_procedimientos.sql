-- =====================================================================
-- V3: Seeds de procedimientos dentales
-- =====================================================================

SET search_path TO clinica;

INSERT INTO procedimiento (codigo, nombre, descripcion, precio_base, duracion_min, requiere_pieza) VALUES
    -- Diagnóstico
    ('CONSULTA',        'Consulta general',           'Evaluación clínica inicial',                     50.00,  30, FALSE),
    ('RADIOGRAFIA',     'Radiografía periapical',     'Radiografía de pieza específica',                30.00,  15, TRUE),
    ('RX_PANORAMICA',   'Radiografía panorámica',     'Radiografía panorámica completa',                60.00,  20, FALSE),

    -- Preventivo
    ('LIMPIEZA',        'Limpieza dental',            'Profilaxis dental con ultrasonido',              80.00,  45, FALSE),
    ('FLUORIZACION',    'Fluorización',               'Aplicación tópica de flúor',                     40.00,  20, FALSE),
    ('SELLANTE',        'Sellante de fosas',          'Sellante preventivo en fosas y fisuras',         50.00,  30, TRUE),

    -- Operatoria
    ('OBTURACION',      'Obturación con resina',      'Restauración directa con resina compuesta',      150.00, 45, TRUE),
    ('OBTURACION_TEMP', 'Obturación temporal',        'Restauración temporal con IRM o similar',        60.00,  30, TRUE),
    ('RECONTORNEO',     'Recontorneo dental',         'Ajuste de forma dental',                         80.00,  30, TRUE),

    -- Endodoncia
    ('ENDODONCIA',      'Endodoncia',                 'Tratamiento de conducto',                        400.00, 90, TRUE),
    ('RETRATAMIENTO',   'Retratamiento endodóntico',  'Repetición de tratamiento de conducto',          500.00, 120, TRUE),

    -- Periodoncia
    ('CURETAJE',        'Curetaje subgingival',       'Raspado y alisado radicular por cuadrante',      120.00, 60, TRUE),
    ('GINGIVECTOMIA',   'Gingivectomía',              'Cirugía de encía',                               200.00, 60, TRUE),

    -- Cirugía
    ('EXTRACCION',      'Extracción simple',          'Extracción de pieza dental erupcionada',         150.00, 45, TRUE),
    ('EXTRACCION_QX',   'Extracción quirúrgica',      'Extracción de pieza retenida o impactada',       300.00, 60, TRUE),
    ('CIRUGIA_3M',      'Cirugía tercer molar',       'Extracción quirúrgica de tercer molar',          450.00, 90, TRUE),

    -- Prótesis
    ('CORONA',          'Corona dental',              'Corona de porcelana sobre diente natural',       600.00, 120, TRUE),
    ('CORONA_TEMP',     'Corona temporal',            'Corona provisional de acrílico',                 100.00, 45, TRUE),
    ('PUENTE',          'Puente fijo',                'Prótesis fija sobre dientes pilares',            800.00, 180, TRUE),
    ('PROTESIS_REM',    'Prótesis removible',         'Prótesis parcial o total removible',             500.00, 180, FALSE),

    -- Implantología
    ('IMPLANTE',        'Implante dental',            'Colocación de implante de titanio',              1200.00, 90, TRUE),
    ('PILAR_IMPLANTE',  'Pilar de implante',          'Colocación de pilar sobre implante',             200.00, 45, TRUE),

    -- Ortodoncia
    ('ORTODONCIA_EVAL', 'Evaluación ortodóncica',     'Diagnóstico y plan de tratamiento ortodóncico',  100.00, 45, FALSE),
    ('BRACKETS',        'Colocación de brackets',     'Aparatología fija completa',                     1500.00, 90, FALSE),
    ('CONTROL_ORTO',    'Control de ortodoncia',      'Ajuste mensual de aparatología',                 80.00,  30, FALSE),

    -- Estética
    ('BLANQUEAMIENTO',  'Blanqueamiento dental',      'Blanqueamiento en consultorio con luz LED',      300.00, 60, FALSE);
