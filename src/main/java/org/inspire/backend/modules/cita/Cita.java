package org.inspire.backend.modules.cita;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;
import org.inspire.backend.common.BaseEntity;
import org.inspire.backend.common.enums.EstadoCita;
import org.inspire.backend.modules.paciente.Paciente;
import org.inspire.backend.modules.persona.Persona;

import java.time.OffsetDateTime;

@Entity
@Table(name = "cita", schema = "clinica")
@SQLDelete(sql = "UPDATE clinica.cita SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class Cita extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "odontologo_id", nullable = false)
    private Persona odontologo;

    @Column(nullable = false)
    private OffsetDateTime fechaHoraInicio;

    @Column(nullable = false)
    private OffsetDateTime fechaHoraFin;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "estado_cita")
    private EstadoCita estado = EstadoCita.PROGRAMADA;

    @Column(nullable = false, length = 255)
    private String motivo = "";
}
