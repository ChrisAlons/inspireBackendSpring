package org.inspire.backend.modules.odontograma;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.inspire.backend.common.BaseEntity;
import org.inspire.backend.modules.atencion.Atencion;
import org.inspire.backend.modules.historiaclinica.HistoriaClinica;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "odontograma", schema = "clinica")
@SQLDelete(sql = "UPDATE clinica.odontograma SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class Odontograma extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historia_clinica_id", nullable = false)
    private HistoriaClinica historiaClinica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atencion_id", nullable = false)
    private Atencion atencion;

    @Column(nullable = false)
    private OffsetDateTime fecha = OffsetDateTime.now(ZoneOffset.UTC);

    @Column(name = "is_inicial", nullable = false)
    private boolean isInicial = false;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String observaciones = "";
}
