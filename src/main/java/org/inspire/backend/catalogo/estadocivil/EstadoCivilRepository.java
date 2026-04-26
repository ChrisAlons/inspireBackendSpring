package org.inspire.backend.catalogo.estadocivil;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoCivilRepository extends JpaRepository<EstadoCivil, Short> {
    Optional<EstadoCivil> findByCodigo(String codigo);
}
