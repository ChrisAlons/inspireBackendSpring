package org.inspire.backend.modules.atencion;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.common.exception.BusinessException;
import org.inspire.backend.common.exception.ResourceNotFoundException;
import org.inspire.backend.modules.atencion.dto.AtencionResponse;
import org.inspire.backend.modules.atencion.dto.CreateAtencionDto;
import org.inspire.backend.modules.atencion.dto.UpdateAtencionDto;
import org.inspire.backend.modules.cita.Cita;
import org.inspire.backend.modules.cita.CitaRepository;
import org.inspire.backend.modules.historiaclinica.HistoriaClinica;
import org.inspire.backend.modules.historiaclinica.HistoriaClinicaRepository;
import org.inspire.backend.modules.persona.Persona;
import org.inspire.backend.modules.persona.PersonaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class AtencionService {

    private final AtencionRepository atencionRepo;
    private final CitaRepository citaRepo;
    private final HistoriaClinicaRepository historiaRepo;
    private final PersonaRepository personaRepo;

    @Transactional
    public AtencionResponse crear(@Valid CreateAtencionDto dto) {
        Cita cita = citaRepo.findById(dto.citaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada: " + dto.citaId()));

        if (atencionRepo.findByCitaId(dto.citaId()).isPresent()) {
            throw new BusinessException(
                    "La cita ya tiene una atención registrada");
        }

        HistoriaClinica historia = historiaRepo.findById(dto.historiaClinicaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historia clínica no encontrada: " + dto.historiaClinicaId()));

        Persona odontologo = personaRepo.findById(dto.odontologoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Odontólogo no encontrado: " + dto.odontologoId()));

        Atencion atencion = new Atencion();
        atencion.setCita(cita);
        atencion.setHistoriaClinica(historia);
        atencion.setOdontologo(odontologo);
        atencion.setNotas(dto.notas() != null ? dto.notas() : "");

        Atencion saved = atencionRepo.save(atencion);
        return AtencionMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public AtencionResponse obtenerPorId(UUID id) {
        Atencion atencion = atencionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Atención no encontrada: " + id));
        return AtencionMapper.toResponse(atencion);
    }

    @Transactional(readOnly = true)
    public AtencionResponse obtenerPorCitaId(UUID citaId) {
        Atencion atencion = atencionRepo.findByCitaId(citaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Atención no encontrada para la cita: " + citaId));
        return AtencionMapper.toResponse(atencion);
    }

    @Transactional(readOnly = true)
    public List<AtencionResponse> listarPorHistoria(UUID historiaClinicaId) {
        List<Atencion> atenciones = atencionRepo.findByHistoriaClinicaId(historiaClinicaId);
        return atenciones.stream()
                .map(AtencionMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AtencionResponse> listarEnCurso() {
        List<Atencion> atenciones = atencionRepo.findByFechaFinIsNull();
        return atenciones.stream()
                .map(AtencionMapper::toResponse)
                .toList();
    }

    @Transactional
    public AtencionResponse actualizar(UUID id, UpdateAtencionDto dto) {
        Atencion atencion = atencionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Atención no encontrada: " + id));

        if (dto.notas() != null) {
            atencion.setNotas(dto.notas());
        }

        Atencion updated = atencionRepo.save(atencion);
        return AtencionMapper.toResponse(updated);
    }

    @Transactional
    public AtencionResponse cerrar(UUID id) {
        Atencion atencion = atencionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Atención no encontrada: " + id));

        if (atencion.getFechaFin() != null) {
            throw new BusinessException("La atención ya fue cerrada");
        }

        atencion.setFechaFin(OffsetDateTime.now(ZoneOffset.UTC));
        Atencion updated = atencionRepo.save(atencion);
        return AtencionMapper.toResponse(updated);
    }

    @Transactional
    public void eliminar(UUID id) {
        Atencion atencion = atencionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Atención no encontrada: " + id));
        atencionRepo.delete(atencion);
    }
}
