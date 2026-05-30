package org.inspire.backend.catalogo.estadocivil;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estado_civil", schema = "clinica")
@Getter
@Setter
@NoArgsConstructor
public class EstadoCivil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Short id;

    @Column(nullable = false, length = 20)
    private String codigo;

    @Column(nullable = false, length = 50)
    private String descripcion;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
