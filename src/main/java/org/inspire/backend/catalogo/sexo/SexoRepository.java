package org.inspire.backend.catalogo.sexo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SexoRepository extends JpaRepository<Sexo, Short> {
    Optional<Sexo> findByCodigo(String codigo);
}
