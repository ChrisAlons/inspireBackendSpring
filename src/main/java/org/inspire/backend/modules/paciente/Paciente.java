package org.inspire.backend.modules.paciente;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.inspire.backend.catalogo.estadocivil.EstadoCivil;
import org.inspire.backend.catalogo.gradoinstruccion.GradoInstruccion;
import org.inspire.backend.common.BaseEntity;
import org.inspire.backend.modules.historiaclinica.HistoriaClinica;
import org.inspire.backend.modules.persona.Persona;

@Entity
@Table(name = "paciente", schema = "clinica")
@SQLDelete(sql = "UPDATE clinica.paciente SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class Paciente extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historia_clinica_id")
    private HistoriaClinica historiaClinica;

    @Column(nullable = false, length = 20)
    private String codigoHistoria;

    @Column(nullable = false, length = 120)
    private String lugarNacimiento = "";

    @Column(nullable = false, length = 120)
    private String procedencia = "";

    @Column(nullable = false, columnDefinition = "TEXT")
    private String viajesUltimoAnio = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grado_instruccion_id", nullable = false)
    private GradoInstruccion gradoInstruccion;

    @Column(nullable = false, length = 120)
    private String ocupacion = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_civil_id", nullable = false)
    private EstadoCivil estadoCivil;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
