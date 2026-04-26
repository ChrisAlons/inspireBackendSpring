package org.inspire.backend.modules.tratamientoejecutado;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TratamientoEjecutadoRepository extends JpaRepository<TratamientoEjecutado, UUID> {
    List<TratamientoEjecutado> findByAtencionId(UUID atencionId);
    List<TratamientoEjecutado> findByPlanDetalleId(UUID planDetalleId);
}
