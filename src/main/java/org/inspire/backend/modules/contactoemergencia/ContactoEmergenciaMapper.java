package org.inspire.backend.modules.contactoemergencia;

import org.inspire.backend.modules.contactoemergencia.dto.ContactoEmergenciaResponse;

class ContactoEmergenciaMapper {

    private ContactoEmergenciaMapper() {}

    static ContactoEmergenciaResponse toResponse(ContactoEmergencia c) {
        return new ContactoEmergenciaResponse(
                c.getId(),
                c.getPaciente().getId(),
                c.getNombresCompletos(),
                c.getCelular(),
                c.getParentesco().getCodigo(),
                c.isApoderado(),
                c.getPrioridad(),
                c.getCreatedAt(),
                c.getUpdatedAt()
        );
    }
}
