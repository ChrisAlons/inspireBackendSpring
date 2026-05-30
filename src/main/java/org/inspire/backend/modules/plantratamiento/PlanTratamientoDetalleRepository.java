package org.inspire.backend.modules.plantratamiento;

import org.inspire.backend.common.enums.EstadoDetallePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlanTratamientoDetalleRepository extends JpaRepository<PlanTratamientoDetalle, UUID> {
    List<PlanTratamientoDetalle> findByPlanTratamientoIdOrderByOrden(UUID planTratamientoId);
    List<PlanTratamientoDetalle> findByPlanTratamientoIdAndEstado(UUID planTratamientoId, EstadoDetallePlan estado);
}
