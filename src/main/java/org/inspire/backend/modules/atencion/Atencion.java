package org.inspire.backend.modules.atencion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.inspire.backend.common.BaseEntity;
import org.inspire.backend.modules.cita.Cita;
import org.inspire.backend.modules.historiaclinica.HistoriaClinica;
import org.inspire.backend.modules.persona.Persona;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "atencion", schema = "clinica")
@SQLDelete(sql = "UPDATE clinica.atencion SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class Atencion extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historia_clinica_id", nullable = false)
    private HistoriaClinica historiaClinica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "odontologo_id", nullable = false)
    private Persona odontologo;

    @Column(nullable = false)
    private OffsetDateTime fechaInicio = OffsetDateTime.now(ZoneOffset.UTC);

    /** NULL = en curso */
    private OffsetDateTime fechaFin;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String notas = "";
}
