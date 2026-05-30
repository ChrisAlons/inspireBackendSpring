package org.inspire.backend.modules.paciente;

import org.inspire.backend.modules.paciente.dto.PacienteResponse;
import org.inspire.backend.modules.persona.Persona;

class PacienteMapper {

    private PacienteMapper() {}

    static PacienteResponse toResponse(Paciente p) {
        Persona per = p.getPersona();
        return new PacienteResponse(
                p.getId(),
                per.getId(),
                per.getTipoDocumento().getCodigo(),
                per.getNumeroDocumento(),
                per.getNombres(),
                per.getApellidoPaterno(),
                per.getApellidoMaterno(),
                per.getFechaNacimiento(),
                per.getSexo().getCodigo(),
                per.getCelular(),
                per.getEmail(),
                per.getDireccion(),
                p.getCodigoHistoria(),
                p.getLugarNacimiento(),
                p.getProcedencia(),
                p.getViajesUltimoAnio(),
                p.getGradoInstruccion().getCodigo(),
                p.getOcupacion(),
                p.getEstadoCivil().getCodigo(),
                p.isActive(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
