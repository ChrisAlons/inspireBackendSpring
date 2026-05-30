package org.inspire.backend.modules.contactoemergencia;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.inspire.backend.catalogo.parentesco.Parentesco;
import org.inspire.backend.common.BaseEntity;
import org.inspire.backend.modules.paciente.Paciente;

@Entity
@Table(name = "contacto_emergencia", schema = "clinica")
@SQLDelete(sql = "UPDATE clinica.contacto_emergencia SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class ContactoEmergencia extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(nullable = false, length = 200)
    private String nombresCompletos;

    @Column(nullable = false, length = 20)
    private String celular;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentesco_id", nullable = false)
    private Parentesco parentesco;

    @Column(name = "is_apoderado", nullable = false)
    private boolean isApoderado = false;

    @Column(nullable = false)
    private Short prioridad = 1;
}
