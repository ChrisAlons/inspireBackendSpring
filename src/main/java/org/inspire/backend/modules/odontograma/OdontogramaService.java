package org.inspire.backend.modules.odontograma;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.common.exception.ResourceNotFoundException;
import org.inspire.backend.modules.atencion.Atencion;
import org.inspire.backend.modules.atencion.AtencionRepository;
import org.inspire.backend.modules.historiaclinica.HistoriaClinica;
import org.inspire.backend.modules.historiaclinica.HistoriaClinicaRepository;
import org.inspire.backend.modules.odontograma.dto.CreateOdontogramaDto;
import org.inspire.backend.modules.odontograma.dto.OdontogramaResponse;
import org.inspire.backend.modules.odontograma.dto.UpdateOdontogramaDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class OdontogramaService {

    private final OdontogramaRepository odontogramaRepo;
    private final HistoriaClinicaRepository historiaRepo;
    private final AtencionRepository atencionRepo;

    @Transactional
    public OdontogramaResponse crear(@Valid CreateOdontogramaDto dto) {
        HistoriaClinica historia = historiaRepo.findById(dto.historiaClinicaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historia clínica no encontrada: " + dto.historiaClinicaId()));

        Atencion atencion = atencionRepo.findById(dto.atencionId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Atención no encontrada: " + dto.atencionId()));

        Odontograma odontograma = new Odontograma();
        odontograma.setHistoriaClinica(historia);
        odontograma.setAtencion(atencion);
        odontograma.setInicial(dto.isInicial() != null ? dto.isInicial() : false);
        odontograma.setObservaciones(dto.observaciones() != null ? dto.observaciones() : "");

        Odontograma saved = odontogramaRepo.save(odontograma);
        return OdontogramaMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public OdontogramaResponse obtenerPorId(UUID id) {
        Odontograma odontograma = odontogramaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Odontograma no encontrado: " + id));
        return OdontogramaMapper.toResponse(odontograma);
    }

    @Transactional(readOnly = true)
    public List<OdontogramaResponse> listarPorHistoria(UUID historiaClinicaId) {
        List<Odontograma> odontogramas = odontogramaRepo.findByHistoriaClinicaIdOrderByFechaDesc(historiaClinicaId);
        return odontogramas.stream()
                .map(OdontogramaMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OdontogramaResponse> listarPorAtencion(UUID atencionId) {
        List<Odontograma> odontogramas = odontogramaRepo.findByAtencionId(atencionId);
        return odontogramas.stream()
                .map(OdontogramaMapper::toResponse)
                .toList();
    }

    @Transactional
    public OdontogramaResponse actualizar(UUID id, UpdateOdontogramaDto dto) {
        Odontograma odontograma = odontogramaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Odontograma no encontrado: " + id));

        if (dto.observaciones() != null) odontograma.setObservaciones(dto.observaciones());

        Odontograma updated = odontogramaRepo.save(odontograma);
        return OdontogramaMapper.toResponse(updated);
    }

    @Transactional
    public void eliminar(UUID id) {
        Odontograma odontograma = odontogramaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Odontograma no encontrado: " + id));
        odontogramaRepo.delete(odontograma);
    }
}
