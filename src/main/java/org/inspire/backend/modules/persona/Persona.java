package org.inspire.backend.modules.persona;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.inspire.backend.catalogo.sexo.Sexo;
import org.inspire.backend.catalogo.tipodocumento.TipoDocumento;
import org.inspire.backend.common.BaseEntity;

import java.time.LocalDate;

@Entity
@Table(name = "persona", schema = "clinica")
@SQLDelete(sql = "UPDATE clinica.persona SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class Persona extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_documento_id", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(nullable = false, length = 20)
    private String numeroDocumento;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidoPaterno;

    @Column(nullable = false, length = 100)
    private String apellidoMaterno = "";

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sexo_id", nullable = false)
    private Sexo sexo;

    @Column(nullable = false, length = 20)
    private String celular = "";

    @Column(nullable = false, length = 120)
    private String email = "";

    @Column(nullable = false, length = 255)
    private String direccion = "";
}
