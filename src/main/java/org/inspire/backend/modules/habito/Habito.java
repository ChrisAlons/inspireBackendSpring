package org.inspire.backend.modules.habito;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;
import org.inspire.backend.common.BaseEntity;
import org.inspire.backend.common.enums.FrecuenciaHabito;
import org.inspire.backend.common.enums.TipoHabito;
import org.inspire.backend.modules.historiaclinica.HistoriaClinica;

@Entity
@Table(name = "habito", schema = "clinica")
@SQLDelete(sql = "UPDATE clinica.habito SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class Habito extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historia_clinica_id", nullable = false)
    private HistoriaClinica historiaClinica;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "tipo_habito")
    private TipoHabito tipo;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "frecuencia_habito")
    private FrecuenciaHabito frecuencia;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String detalle = "";
}
