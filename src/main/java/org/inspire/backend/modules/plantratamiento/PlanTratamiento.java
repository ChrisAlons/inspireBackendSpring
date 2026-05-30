package org.inspire.backend.modules.plantratamiento;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;
import org.inspire.backend.common.BaseEntity;
import org.inspire.backend.common.enums.EstadoPlan;
import org.inspire.backend.modules.atencion.Atencion;
import org.inspire.backend.modules.historiaclinica.HistoriaClinica;

import java.math.BigDecimal;

@Entity
@Table(name = "plan_tratamiento", schema = "clinica")
@SQLDelete(sql = "UPDATE clinica.plan_tratamiento SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class PlanTratamiento extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atencion_id", nullable = false)
    private Atencion atencion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historia_clinica_id", nullable = false)
    private HistoriaClinica historiaClinica;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "estado_plan")
    private EstadoPlan estado = EstadoPlan.PROPUESTO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal = BigDecimal.ZERO;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String observaciones = "";

    @Column(name = "is_documento_impreso", nullable = false)
    private boolean isDocumentoImpreso = false;
}
