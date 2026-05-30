package org.inspire.backend.modules.antecedente;

import org.inspire.backend.modules.antecedente.dto.AntecedenteFamiliarResponse;

class AntecedenteFamiliarMapper {

    private AntecedenteFamiliarMapper() {}

    static AntecedenteFamiliarResponse toResponse(AntecedenteFamiliar a) {
        return new AntecedenteFamiliarResponse(
                a.getId(),
                a.getHistoriaClinica().getId(),
                a.getParentesco().getCodigo(),
                a.getDescripcion(),
                a.getCreatedAt(),
                a.getUpdatedAt()
        );
    }
}
