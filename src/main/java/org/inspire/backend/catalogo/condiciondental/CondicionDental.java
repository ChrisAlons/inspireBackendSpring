package org.inspire.backend.catalogo.condiciondental;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "condicion_dental", schema = "clinica")
@Getter
@Setter
@NoArgsConstructor
public class CondicionDental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Short id;

    @Column(nullable = false, length = 30)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String descripcion;

    @Column(nullable = false, length = 7)
    private String colorHex = "#000000";

    @Column(nullable = false)
    private boolean requiereCara = true;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
