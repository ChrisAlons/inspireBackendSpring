package org.inspire.backend.catalogo.procedimiento;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.inspire.backend.common.BaseEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "procedimiento", schema = "clinica")
@SQLDelete(sql = "UPDATE clinica.procedimiento SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class Procedimiento extends BaseEntity {

    @Column(nullable = false, length = 20, unique = true)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion = "";

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioBase = BigDecimal.ZERO;

    @Column(nullable = false)
    private Short duracionMin = 30;

    @Column(nullable = false)
    private boolean requierePieza = true;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
