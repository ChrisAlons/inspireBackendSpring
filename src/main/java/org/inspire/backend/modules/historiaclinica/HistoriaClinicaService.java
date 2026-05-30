package org.inspire.backend.modules.historiaclinica;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.common.exception.BusinessException;
import org.inspire.backend.common.exception.ResourceNotFoundException;
import org.inspire.backend.modules.historiaclinica.dto.CreateHistoriaClinicaDto;
import org.inspire.backend.modules.historiaclinica.dto.HistoriaClinicaResponse;
import org.inspire.backend.modules.historiaclinica.dto.UpdateHistoriaClinicaDto;
import org.inspire.backend.modules.paciente.Paciente;
import org.inspire.backend.modules.paciente.PacienteRepository;
import org.inspire.backend.modules.persona.Persona;
import org.inspire.backend.modules.persona.PersonaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class HistoriaClinicaService {

    private final HistoriaClinicaRepository historiaRepo;
    private final PacienteRepository pacienteRepo;
    private final PersonaRepository personaRepo;

    @Transactional
    public HistoriaClinicaResponse crear(@Valid CreateHistoriaClinicaDto dto) {
        Paciente paciente = pacienteRepo.findById(dto.pacienteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente no encontrado: " + dto.pacienteId()));

        if (historiaRepo.findByPacienteId(dto.pacienteId()).isPresent()) {
            throw new BusinessException(
                    "El paciente ya tiene una historia clínica abierta");
        }

        Persona odontologo = personaRepo.findById(dto.odontologoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Odontólogo no encontrado: " + dto.odontologoId()));

        HistoriaClinica historia = new HistoriaClinica();
        historia.setPaciente(paciente);
        historia.setOdontologo(odontologo);
        historia.setFechaApertura(dto.fechaApertura() != null ? dto.fechaApertura() : LocalDate.now());
        historia.setObservacionesGenerales(dto.observacionesGenerales() != null ? dto.observacionesGenerales() : "");

        HistoriaClinica saved = historiaRepo.save(historia);
        return HistoriaClinicaMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public HistoriaClinicaResponse obtenerPorId(UUID id) {
        HistoriaClinica historia = historiaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historia clínica no encontrada: " + id));
        return HistoriaClinicaMapper.toResponse(historia);
    }

    @Transactional(readOnly = true)
    public HistoriaClinicaResponse obtenerPorPaciente(UUID pacienteId) {
        HistoriaClinica historia = historiaRepo.findByPacienteId(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historia clínica no encontrada para paciente: " + pacienteId));
        return HistoriaClinicaMapper.toResponse(historia);
    }

    @Transactional
    public HistoriaClinicaResponse actualizar(UUID id, UpdateHistoriaClinicaDto dto) {
        HistoriaClinica historia = historiaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historia clínica no encontrada: " + id));

        if (dto.observacionesGenerales() != null) {
            historia.setObservacionesGenerales(dto.observacionesGenerales());
        }

        if (dto.odontologoId() != null) {
            Persona odontologo = personaRepo.findById(dto.odontologoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Odontólogo no encontrado: " + dto.odontologoId()));
            historia.setOdontologo(odontologo);
        }

        HistoriaClinica updated = historiaRepo.save(historia);
        return HistoriaClinicaMapper.toResponse(updated);
    }

    @Transactional
    public void eliminar(UUID id) {
        HistoriaClinica historia = historiaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historia clínica no encontrada: " + id));
        historiaRepo.delete(historia);
    }
}
