package org.inspire.backend.modules.antecedente;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.inspire.backend.catalogo.parentesco.Parentesco;
import org.inspire.backend.common.BaseEntity;
import org.inspire.backend.modules.historiaclinica.HistoriaClinica;

@Entity
@Table(name = "antecedente_familiar", schema = "clinica")
@SQLDelete(sql = "UPDATE clinica.antecedente_familiar SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class AntecedenteFamiliar extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historia_clinica_id", nullable = false)
    private HistoriaClinica historiaClinica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentesco_id", nullable = false)
    private Parentesco parentesco;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;
}
