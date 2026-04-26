package org.inspire.backend.modules.odontograma;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OdontogramaRepository extends JpaRepository<Odontograma, UUID> {
    List<Odontograma> findByHistoriaClinicaIdOrderByFechaDesc(UUID historiaClinicaId);
    List<Odontograma> findByAtencionId(UUID atencionId);
}
