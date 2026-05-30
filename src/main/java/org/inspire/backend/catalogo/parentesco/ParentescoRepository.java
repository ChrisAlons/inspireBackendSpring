package org.inspire.backend.catalogo.parentesco;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParentescoRepository extends JpaRepository<Parentesco, Short> {
    Optional<Parentesco> findByCodigo(String codigo);
}
