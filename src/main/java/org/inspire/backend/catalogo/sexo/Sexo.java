package org.inspire.backend.catalogo.sexo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sexo", schema = "clinica")
@Getter
@Setter
@NoArgsConstructor
public class Sexo {

    @Id
    @Column(updatable = false)
    private Short id;

    @Column(nullable = false, length = 1)
    private String codigo;

    @Column(nullable = false, length = 20)
    private String descripcion;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
