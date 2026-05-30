package org.inspire.backend.modules.persona;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.catalogo.sexo.Sexo;
import org.inspire.backend.catalogo.sexo.SexoRepository;
import org.inspire.backend.catalogo.tipodocumento.TipoDocumento;
import org.inspire.backend.catalogo.tipodocumento.TipoDocumentoRepository;
import org.inspire.backend.common.exception.BusinessException;
import org.inspire.backend.common.exception.ConflictException;
import org.inspire.backend.common.exception.ResourceNotFoundException;
import org.inspire.backend.modules.persona.dto.CreatePersonaDto;
import org.inspire.backend.modules.persona.dto.PersonaResponse;
import org.inspire.backend.modules.persona.dto.UpdatePersonaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class PersonaService {

    private final PersonaRepository personaRepo;
    private final TipoDocumentoRepository tipoDocumentoRepo;
    private final SexoRepository sexoRepo;

    @Transactional
    public PersonaResponse crear(@Valid CreatePersonaDto dto) {
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

        Persona saved = personaRepo.save(persona);
        return PersonaMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<PersonaResponse> buscar(String q, Pageable pageable) {
        Page<Persona> page = (q == null || q.isBlank())
                ? personaRepo.findAll(pageable)
                : personaRepo.buscar(q, pageable);
        return page.map(PersonaMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PersonaResponse obtenerPorId(UUID id) {
        Persona persona = personaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Persona no encontrada: " + id));
        return PersonaMapper.toResponse(persona);
    }

    @Transactional
    public PersonaResponse actualizar(UUID id, UpdatePersonaDto dto) {
        Persona persona = personaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Persona no encontrada: " + id));

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

        Persona updated = personaRepo.save(persona);
        return PersonaMapper.toResponse(updated);
    }

    @Transactional
    public void eliminar(UUID id) {
        Persona persona = personaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Persona no encontrada: " + id));
        personaRepo.delete(persona);
    }
}
