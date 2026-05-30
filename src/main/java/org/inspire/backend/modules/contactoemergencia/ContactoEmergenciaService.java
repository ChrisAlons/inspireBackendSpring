package org.inspire.backend.modules.contactoemergencia;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.catalogo.parentesco.Parentesco;
import org.inspire.backend.catalogo.parentesco.ParentescoRepository;
import org.inspire.backend.common.exception.BusinessException;
import org.inspire.backend.common.exception.ResourceNotFoundException;
import org.inspire.backend.modules.contactoemergencia.dto.ContactoEmergenciaResponse;
import org.inspire.backend.modules.contactoemergencia.dto.CreateContactoEmergenciaDto;
import org.inspire.backend.modules.contactoemergencia.dto.UpdateContactoEmergenciaDto;
import org.inspire.backend.modules.paciente.Paciente;
import org.inspire.backend.modules.paciente.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class ContactoEmergenciaService {

    private final ContactoEmergenciaRepository contactoRepo;
    private final PacienteRepository pacienteRepo;
    private final ParentescoRepository parentescoRepo;

    @Transactional
    public ContactoEmergenciaResponse crear(UUID pacienteId, @Valid CreateContactoEmergenciaDto dto) {
        Paciente paciente = pacienteRepo.findById(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente no encontrado: " + pacienteId));

        Parentesco parentesco = parentescoRepo.findByCodigo(dto.parentescoCodigo())
                .orElseThrow(() -> new BusinessException(
                        "Parentesco no encontrado: " + dto.parentescoCodigo()));

        ContactoEmergencia contacto = new ContactoEmergencia();
        contacto.setPaciente(paciente);
        contacto.setNombresCompletos(dto.nombresCompletos());
        contacto.setCelular(dto.celular());
        contacto.setParentesco(parentesco);
        contacto.setApoderado(dto.isApoderado() != null ? dto.isApoderado() : false);
        contacto.setPrioridad(dto.prioridad() != null ? dto.prioridad() : 1);

        ContactoEmergencia saved = contactoRepo.save(contacto);
        return ContactoEmergenciaMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ContactoEmergenciaResponse> listarPorPaciente(UUID pacienteId) {
        List<ContactoEmergencia> contactos = contactoRepo.findByPacienteIdOrderByPrioridad(pacienteId);
        return contactos.stream()
                .map(ContactoEmergenciaMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ContactoEmergenciaResponse obtenerPorId(UUID id) {
        ContactoEmergencia contacto = contactoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contacto de emergencia no encontrado: " + id));
        return ContactoEmergenciaMapper.toResponse(contacto);
    }

    @Transactional
    public ContactoEmergenciaResponse actualizar(UUID id, UpdateContactoEmergenciaDto dto) {
        ContactoEmergencia contacto = contactoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contacto de emergencia no encontrado: " + id));

        if (dto.nombresCompletos() != null) contacto.setNombresCompletos(dto.nombresCompletos());
        if (dto.celular() != null) contacto.setCelular(dto.celular());
        if (dto.isApoderado() != null) contacto.setApoderado(dto.isApoderado());
        if (dto.prioridad() != null) contacto.setPrioridad(dto.prioridad());

        if (dto.parentescoCodigo() != null) {
            Parentesco parentesco = parentescoRepo.findByCodigo(dto.parentescoCodigo())
                    .orElseThrow(() -> new BusinessException(
                            "Parentesco no encontrado: " + dto.parentescoCodigo()));
            contacto.setParentesco(parentesco);
        }

        ContactoEmergencia updated = contactoRepo.save(contacto);
        return ContactoEmergenciaMapper.toResponse(updated);
    }

    @Transactional
    public void eliminar(UUID id) {
        ContactoEmergencia contacto = contactoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contacto de emergencia no encontrado: " + id));
        contactoRepo.delete(contacto);
    }
}
