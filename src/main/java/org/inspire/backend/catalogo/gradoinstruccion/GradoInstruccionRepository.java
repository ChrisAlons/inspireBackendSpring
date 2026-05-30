package org.inspire.backend.catalogo.gradoinstruccion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradoInstruccionRepository extends JpaRepository<GradoInstruccion, Short> {
    Optional<GradoInstruccion> findByCodigo(String codigo);
}
