package org.inspire.backend.modules.plantratamiento;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.inspire.backend.common.enums.EstadoPlan;
import org.inspire.backend.modules.persona.Persona;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/** Registro inmutable de cada cambio de estado del plan. Sin updated_at ni deleted_at. */
@Entity
@Table(name = "plan_tratamiento_evento", schema = "clinica")
@Getter
@Setter
@NoArgsConstructor
public class PlanTratamientoEvento {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_tratamiento_id", nullable = false, updatable = false)
    private PlanTratamiento planTratamiento;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "estado_plan", updatable = false)
    private EstadoPlan estadoNuevo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_persona_id", nullable = false, updatable = false)
    private Persona actorPersona;

    @Column(nullable = false, length = 200, updatable = false)
    private String aceptadoPor = "";

    @Column(nullable = false, columnDefinition = "TEXT", updatable = false)
    private String notas = "";

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void prePersist() {
        this.createdAt = OffsetDateTime.now(ZoneOffset.UTC);
    }
}
