package org.inspire.backend.modules.odontograma;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;
import org.inspire.backend.catalogo.caradental.CaraDental;
import org.inspire.backend.catalogo.condiciondental.CondicionDental;
import org.inspire.backend.catalogo.piezadental.PiezaDental;
import org.inspire.backend.common.BaseEntity;
import org.inspire.backend.common.enums.EstadoHallazgo;

@Entity
@Table(name = "odontograma_hallazgo", schema = "clinica")
@SQLDelete(sql = "UPDATE clinica.odontograma_hallazgo SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class OdontogramaHallazgo extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "odontograma_id", nullable = false)
    private Odontograma odontograma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pieza_id", nullable = false)
    private PiezaDental pieza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cara_id", nullable = false)
    private CaraDental cara;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condicion_id", nullable = false)
    private CondicionDental condicion;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "estado_hallazgo")
    private EstadoHallazgo estado = EstadoHallazgo.EXISTENTE;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String notas = "";

    @Column(name = "is_registrado_voz", nullable = false)
    private boolean isRegistradoVoz = false;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String transcripcionVoz = "";
}
