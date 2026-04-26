package org.inspire.backend.catalogo.gradoinstruccion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grado_instruccion", schema = "clinica")
@Getter
@Setter
@NoArgsConstructor
public class GradoInstruccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Short id;

    @Column(nullable = false, length = 30)
    private String codigo;

    @Column(nullable = false, length = 80)
    private String descripcion;

    @Column(nullable = false)
    private Short orden;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
