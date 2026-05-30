package org.inspire.backend.modules.plantratamiento;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;
import org.inspire.backend.catalogo.caradental.CaraDental;
import org.inspire.backend.catalogo.piezadental.PiezaDental;
import org.inspire.backend.catalogo.procedimiento.Procedimiento;
import org.inspire.backend.common.BaseEntity;
import org.inspire.backend.common.enums.EstadoDetallePlan;

import java.math.BigDecimal;

@Entity
@Table(name = "plan_tratamiento_detalle", schema = "clinica")
@SQLDelete(sql = "UPDATE clinica.plan_tratamiento_detalle SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class PlanTratamientoDetalle extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_tratamiento_id", nullable = false)
    private PlanTratamiento planTratamiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedimiento_id", nullable = false)
    private Procedimiento procedimiento;

    /** pieza_id=0 es sentinel NO_APLICA para procedimientos sin pieza específica */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pieza_id", nullable = false)
    private PiezaDental pieza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cara_id", nullable = false)
    private CaraDental cara;

    @Column(nullable = false)
    private Short cantidad = 1;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "estado_detalle_plan")
    private EstadoDetallePlan estado = EstadoDetallePlan.PENDIENTE;

    @Column(nullable = false)
    private Short orden = 1;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String notas = "";
}
