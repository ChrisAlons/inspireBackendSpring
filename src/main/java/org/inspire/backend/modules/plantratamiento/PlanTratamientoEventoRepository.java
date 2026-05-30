package org.inspire.backend.modules.plantratamiento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlanTratamientoEventoRepository extends JpaRepository<PlanTratamientoEvento, UUID> {
    List<PlanTratamientoEvento> findByPlanTratamientoIdOrderByCreatedAtAsc(UUID planTratamientoId);
}
