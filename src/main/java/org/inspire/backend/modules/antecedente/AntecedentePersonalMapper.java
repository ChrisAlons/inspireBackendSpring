package org.inspire.backend.modules.antecedente;

import org.inspire.backend.modules.antecedente.dto.AntecedentePersonalResponse;

class AntecedentePersonalMapper {

    private AntecedentePersonalMapper() {}

    static AntecedentePersonalResponse toResponse(AntecedentePersonal a) {
        return new AntecedentePersonalResponse(
                a.getId(),
                a.getHistoriaClinica().getId(),
                a.getDescripcion(),
                a.getFechaRegistro(),
                a.getCreatedAt(),
                a.getUpdatedAt()
        );
    }
}
