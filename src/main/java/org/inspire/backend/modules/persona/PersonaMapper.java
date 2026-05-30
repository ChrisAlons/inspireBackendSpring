package org.inspire.backend.modules.persona;

import org.inspire.backend.modules.persona.dto.PersonaResponse;

class PersonaMapper {

    private PersonaMapper() {}

    static PersonaResponse toResponse(Persona p) {
        return new PersonaResponse(
                p.getId(),
                p.getTipoDocumento().getCodigo(),
                p.getNumeroDocumento(),
                p.getNombres(),
                p.getApellidoPaterno(),
                p.getApellidoMaterno(),
                p.getFechaNacimiento(),
                p.getSexo().getCodigo(),
                p.getCelular(),
                p.getEmail(),
                p.getDireccion(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
