package org.inspire.backend.catalogo.procedimiento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProcedimientoRepository extends JpaRepository<Procedimiento, UUID> {
    Optional<Procedimiento> findByCodigo(String codigo);
}
