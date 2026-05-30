package org.inspire.backend.modules.plantratamiento;

import org.inspire.backend.modules.plantratamiento.dto.PlanDetalleResponse;

class PlanTratamientoDetalleMapper {

    private PlanTratamientoDetalleMapper() {}

    static PlanDetalleResponse toResponse(PlanTratamientoDetalle d) {
        return new PlanDetalleResponse(
                d.getId(),
                d.getPlanTratamiento().getId(),
                d.getProcedimiento().getCodigo(),
                d.getPieza().getId(),
                d.getCara().getCodigo(),
                d.getCantidad(),
                d.getPrecioUnitario(),
                d.getEstado(),
                d.getOrden(),
                d.getNotas(),
                d.getCreatedAt(),
                d.getUpdatedAt()
        );
    }
}
