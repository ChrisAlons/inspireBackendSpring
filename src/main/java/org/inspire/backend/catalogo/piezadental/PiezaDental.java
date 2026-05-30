package org.inspire.backend.catalogo.piezadental;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pieza_dental", schema = "clinica")
@Getter
@Setter
@NoArgsConstructor
public class PiezaDental {

    /** PK natural FDI (11-48 adulto, 51-85 deciduo). id=0 es sentinel NO_APLICA. */
    @Id
    @Column(updatable = false)
    private Short id;

    @Column(nullable = false)
    private Short cuadrante;

    @Column(nullable = false)
    private Short posicion;

    @Column(nullable = false, length = 40)
    private String nombre;

    @Column(name = "is_deciduo", nullable = false)
    private boolean isDeciduo = false;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
