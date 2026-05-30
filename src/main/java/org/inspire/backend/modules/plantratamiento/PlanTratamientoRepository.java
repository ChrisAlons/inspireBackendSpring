package org.inspire.backend.modules.plantratamiento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlanTratamientoRepository extends JpaRepository<PlanTratamiento, UUID> {
    List<PlanTratamiento> findByHistoriaClinicaIdOrderByCreatedAtDesc(UUID historiaClinicaId);
    List<PlanTratamiento> findByAtencionId(UUID atencionId);
}
