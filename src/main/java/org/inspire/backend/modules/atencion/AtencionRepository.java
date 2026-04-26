package org.inspire.backend.modules.atencion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AtencionRepository extends JpaRepository<Atencion, UUID> {
    Optional<Atencion> findByCitaId(UUID citaId);
    List<Atencion> findByHistoriaClinicaId(UUID historiaClinicaId);
    List<Atencion> findByFechaFinIsNull();
}
