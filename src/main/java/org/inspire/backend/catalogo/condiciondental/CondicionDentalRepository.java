package org.inspire.backend.catalogo.condiciondental;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CondicionDentalRepository extends JpaRepository<CondicionDental, Short> {
    Optional<CondicionDental> findByCodigo(String codigo);
}
