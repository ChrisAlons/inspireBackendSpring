package org.inspire.backend.modules.historiaclinica;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.inspire.backend.common.BaseEntity;
import org.inspire.backend.modules.paciente.Paciente;
import org.inspire.backend.modules.persona.Persona;

import java.time.LocalDate;

@Entity
@Table(name = "historia_clinica", schema = "clinica")
@SQLDelete(sql = "UPDATE clinica.historia_clinica SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class HistoriaClinica extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(nullable = false)
    private LocalDate fechaApertura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "odontologo_id", nullable = false)
    private Persona odontologo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String observacionesGenerales = "";
}
