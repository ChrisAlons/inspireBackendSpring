package org.inspire.backend.modules.tratamientoejecutado;

import org.inspire.backend.modules.tratamientoejecutado.dto.TratamientoEjecutadoResponse;

class TratamientoEjecutadoMapper {

    private TratamientoEjecutadoMapper() {}

    static TratamientoEjecutadoResponse toResponse(TratamientoEjecutado t) {
        return new TratamientoEjecutadoResponse(
                t.getId(),
                t.getPlanDetalle().getId(),
                t.getAtencion().getId(),
                t.getOdontologo().getId(),
                t.getFechaEjecucion(),
                t.getObservaciones(),
                t.getCreatedAt(),
                t.getUpdatedAt()
        );
    }
}
