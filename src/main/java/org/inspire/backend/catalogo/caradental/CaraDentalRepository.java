package org.inspire.backend.catalogo.caradental;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CaraDentalRepository extends JpaRepository<CaraDental, Short> {
    Optional<CaraDental> findByCodigo(String codigo);
}
