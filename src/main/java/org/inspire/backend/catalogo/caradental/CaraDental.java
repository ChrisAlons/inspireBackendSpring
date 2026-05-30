package org.inspire.backend.catalogo.caradental;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cara_dental", schema = "clinica")
@Getter
@Setter
@NoArgsConstructor
public class CaraDental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Short id;

    @Column(nullable = false, length = 20)
    private String codigo;

    @Column(nullable = false, length = 50)
    private String descripcion;

    @Column(nullable = false, length = 3)
    private String abreviatura;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
