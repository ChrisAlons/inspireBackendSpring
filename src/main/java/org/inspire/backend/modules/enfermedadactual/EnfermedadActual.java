package org.inspire.backend.modules.enfermedadactual;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.inspire.backend.common.BaseEntity;
import org.inspire.backend.modules.atencion.Atencion;

@Entity
@Table(name = "enfermedad_actual", schema = "clinica")
@SQLDelete(sql = "UPDATE clinica.enfermedad_actual SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class EnfermedadActual extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atencion_id", nullable = false)
    private Atencion atencion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String motivoConsulta;

    @Column(nullable = false, length = 100)
    private String tiempoEnfermedad = "";

    @Column(nullable = false, columnDefinition = "TEXT")
    private String signosSintomas = "";

    @Column(nullable = false, columnDefinition = "TEXT")
    private String funcionesBiologicas = "";

    @Column(nullable = false, columnDefinition = "TEXT")
    private String expectativasTratamiento = "";
}
