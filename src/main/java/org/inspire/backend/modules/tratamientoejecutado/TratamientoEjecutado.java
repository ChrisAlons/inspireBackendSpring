package org.inspire.backend.modules.tratamientoejecutado;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.inspire.backend.common.BaseEntity;
import org.inspire.backend.modules.atencion.Atencion;
import org.inspire.backend.modules.persona.Persona;
import org.inspire.backend.modules.plantratamiento.PlanTratamientoDetalle;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "tratamiento_ejecutado", schema = "clinica")
@SQLDelete(sql = "UPDATE clinica.tratamiento_ejecutado SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class TratamientoEjecutado extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_detalle_id", nullable = false)
    private PlanTratamientoDetalle planDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atencion_id", nullable = false)
    private Atencion atencion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "odontologo_id", nullable = false)
    private Persona odontologo;

    @Column(nullable = false)
    private OffsetDateTime fechaEjecucion = OffsetDateTime.now(ZoneOffset.UTC);

    @Column(nullable = false, columnDefinition = "TEXT")
    private String observaciones = "";
}
