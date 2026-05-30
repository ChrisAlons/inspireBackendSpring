package org.inspire.backend.modules.paciente;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.catalogo.estadocivil.EstadoCivil;
import org.inspire.backend.catalogo.estadocivil.EstadoCivilRepository;
import org.inspire.backend.catalogo.gradoinstruccion.GradoInstruccion;
import org.inspire.backend.catalogo.gradoinstruccion.GradoInstruccionRepository;
import org.inspire.backend.catalogo.sexo.Sexo;
import org.inspire.backend.catalogo.sexo.SexoRepository;
import org.inspire.backend.catalogo.tipodocumento.TipoDocumento;
import org.inspire.backend.catalogo.tipodocumento.TipoDocumentoRepository;
import org.inspire.backend.common.exception.BusinessException;
import org.inspire.backend.common.exception.ConflictException;
import org.inspire.backend.common.exception.ResourceNotFoundException;
import org.inspire.backend.modules.paciente.dto.CreatePacienteDto;
import org.inspire.backend.modules.paciente.dto.PacienteResponse;
import org.inspire.backend.modules.paciente.dto.UpdatePacienteDto;
import org.inspire.backend.modules.persona.Persona;
import org.inspire.backend.modules.persona.PersonaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepo;
    private final PersonaRepository personaRepo;
    private final TipoDocumentoRepository tipoDocumentoRepo;
    private final SexoRepository sexoRepo;
    private final GradoInstruccionRepository gradoInstruccionRepo;
    private final EstadoCivilRepository estadoCivilRepo;

    @Transactional
    public PacienteResponse crear(@Valid CreatePacienteDto dto) {
        TipoDocumento tipoDocumento = tipoDocumentoRepo.findByCodigo(dto.tipoDocumentoCodigo())
                .orElseThrow(() -> new BusinessException(
                        "Tipo de documento no encontrado: " + dto.tipoDocumentoCodigo()));

        if (personaRepo.existsByTipoDocumentoIdAndNumeroDocumento(
                tipoDocumento.getId(), dto.numeroDocumento())) {
            throw new ConflictException(
                    "Ya existe una persona con documento " +
                    dto.tipoDocumentoCodigo() + " " + dto.numeroDocumento());
        }

        Sexo sexo = sexoRepo.findByCodigo(dto.sexoCodigo())
                .orElseThrow(() -> new BusinessException(
                        "Sexo no encontrado: " + dto.sexoCodigo()));

        GradoInstruccion gradoInstruccion = gradoInstruccionRepo.findByCodigo(dto.gradoInstruccionCodigo())
                .orElseThrow(() -> new BusinessException(
                        "Grado de instrucción no encontrado: " + dto.gradoInstruccionCodigo()));

        EstadoCivil estadoCivil = estadoCivilRepo.findByCodigo(dto.estadoCivilCodigo())
                .orElseThrow(() -> new BusinessException(
                        "Estado civil no encontrado: " + dto.estadoCivilCodigo()));

        Persona persona = new Persona();
        persona.setTipoDocumento(tipoDocumento);
        persona.setNumeroDocumento(dto.numeroDocumento());
        persona.setNombres(dto.nombres());
        persona.setApellidoPaterno(dto.apellidoPaterno());
        persona.setApellidoMaterno(dto.apellidoMaterno() != null ? dto.apellidoMaterno() : "");
        persona.setFechaNacimiento(dto.fechaNacimiento());
        persona.setSexo(sexo);
        persona.setCelular(dto.celular() != null ? dto.celular() : "");
        persona.setEmail(dto.email() != null ? dto.email() : "");
        persona.setDireccion(dto.direccion() != null ? dto.direccion() : "");

        Persona savedPersona = personaRepo.save(persona);

        String codigoHistoria = "HC" + String.format("%06d", pacienteRepo.countTotal() + 1);

        Paciente paciente = new Paciente();
        paciente.setPersona(savedPersona);
        paciente.setCodigoHistoria(codigoHistoria);
        paciente.setLugarNacimiento(dto.lugarNacimiento() != null ? dto.lugarNacimiento() : "");
        paciente.setProcedencia(dto.procedencia() != null ? dto.procedencia() : "");
        paciente.setViajesUltimoAnio(dto.viajesUltimoAnio() != null ? dto.viajesUltimoAnio() : "");
        paciente.setGradoInstruccion(gradoInstruccion);
        paciente.setOcupacion(dto.ocupacion() != null ? dto.ocupacion() : "");
        paciente.setEstadoCivil(estadoCivil);

        Paciente saved = pacienteRepo.save(paciente);
        return PacienteMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<PacienteResponse> buscar(String q, Pageable pageable) {
        Page<Paciente> page = (q == null || q.isBlank())
                ? pacienteRepo.findAll(pageable)
                : pacienteRepo.buscar(q, pageable);
        return page.map(PacienteMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PacienteResponse obtenerPorId(UUID id) {
        Paciente paciente = pacienteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente no encontrado: " + id));
        return PacienteMapper.toResponse(paciente);
    }

    @Transactional
    public PacienteResponse actualizar(UUID id, UpdatePacienteDto dto) {
        Paciente paciente = pacienteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente no encontrado: " + id));

        Persona persona = paciente.getPersona();

        boolean tipoChanged = dto.tipoDocumentoCodigo() != null;
        boolean nroChanged  = dto.numeroDocumento() != null;

        if (tipoChanged || nroChanged) {
            TipoDocumento tipoParaCheck;
            if (tipoChanged) {
                tipoParaCheck = tipoDocumentoRepo.findByCodigo(dto.tipoDocumentoCodigo())
                        .orElseThrow(() -> new BusinessException(
                                "Tipo de documento no encontrado: " + dto.tipoDocumentoCodigo()));
            } else {
                tipoParaCheck = persona.getTipoDocumento();
            }

            String nroParaCheck = nroChanged ? dto.numeroDocumento() : persona.getNumeroDocumento();

            boolean sinCambio =
                    tipoParaCheck.getId().equals(persona.getTipoDocumento().getId()) &&
                    nroParaCheck.equals(persona.getNumeroDocumento());

            if (!sinCambio && personaRepo.existsByTipoDocumentoIdAndNumeroDocumento(
                    tipoParaCheck.getId(), nroParaCheck)) {
                throw new ConflictException(
                        "Ya existe una persona con documento " +
                        tipoParaCheck.getCodigo() + " " + nroParaCheck);
            }

            if (tipoChanged) persona.setTipoDocumento(tipoParaCheck);
            if (nroChanged)  persona.setNumeroDocumento(dto.numeroDocumento());
        }

        if (dto.nombres() != null)         persona.setNombres(dto.nombres());
        if (dto.apellidoPaterno() != null)  persona.setApellidoPaterno(dto.apellidoPaterno());
        if (dto.apellidoMaterno() != null)  persona.setApellidoMaterno(dto.apellidoMaterno());
        if (dto.fechaNacimiento() != null)  persona.setFechaNacimiento(dto.fechaNacimiento());
        if (dto.sexoCodigo() != null) {
            Sexo sexo = sexoRepo.findByCodigo(dto.sexoCodigo())
                    .orElseThrow(() -> new BusinessException(
                            "Sexo no encontrado: " + dto.sexoCodigo()));
            persona.setSexo(sexo);
        }
        if (dto.celular() != null)   persona.setCelular(dto.celular());
        if (dto.email() != null)     persona.setEmail(dto.email());
        if (dto.direccion() != null) persona.setDireccion(dto.direccion());

        if (dto.lugarNacimiento() != null)  paciente.setLugarNacimiento(dto.lugarNacimiento());
        if (dto.procedencia() != null)      paciente.setProcedencia(dto.procedencia());
        if (dto.viajesUltimoAnio() != null) paciente.setViajesUltimoAnio(dto.viajesUltimoAnio());
        if (dto.ocupacion() != null)        paciente.setOcupacion(dto.ocupacion());

        if (dto.gradoInstruccionCodigo() != null) {
            GradoInstruccion grado = gradoInstruccionRepo.findByCodigo(dto.gradoInstruccionCodigo())
                    .orElseThrow(() -> new BusinessException(
                            "Grado de instrucción no encontrado: " + dto.gradoInstruccionCodigo()));
            paciente.setGradoInstruccion(grado);
        }

        if (dto.estadoCivilCodigo() != null) {
            EstadoCivil estadoCivil = estadoCivilRepo.findByCodigo(dto.estadoCivilCodigo())
                    .orElseThrow(() -> new BusinessException(
                            "Estado civil no encontrado: " + dto.estadoCivilCodigo()));
            paciente.setEstadoCivil(estadoCivil);
        }

        personaRepo.save(persona);
        Paciente updated = pacienteRepo.save(paciente);
        return PacienteMapper.toResponse(updated);
    }

    @Transactional
    public void eliminar(UUID id) {
        Paciente paciente = pacienteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente no encontrado: " + id));
        pacienteRepo.delete(paciente);
    }
}
