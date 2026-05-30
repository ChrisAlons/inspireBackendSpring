package org.inspire.backend.modules.habito;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.common.exception.BusinessException;
import org.inspire.backend.common.exception.ResourceNotFoundException;
import org.inspire.backend.modules.habito.dto.CreateHabitoDto;
import org.inspire.backend.modules.habito.dto.HabitoResponse;
import org.inspire.backend.modules.habito.dto.UpdateHabitoDto;
import org.inspire.backend.modules.historiaclinica.HistoriaClinica;
import org.inspire.backend.modules.historiaclinica.HistoriaClinicaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class HabitoService {

    private final HabitoRepository habitoRepo;
    private final HistoriaClinicaRepository historiaRepo;

    @Transactional
    public HabitoResponse crear(@Valid CreateHabitoDto dto) {
        HistoriaClinica historia = historiaRepo.findById(dto.historiaClinicaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historia clínica no encontrada: " + dto.historiaClinicaId()));

        if (habitoRepo.findByHistoriaClinicaIdAndTipo(dto.historiaClinicaId(), dto.tipo()).isPresent()) {
            throw new BusinessException(
                    "Ya existe un registro de hábito tipo " + dto.tipo() + " para esta historia clínica");
        }

        Habito habito = new Habito();
        habito.setHistoriaClinica(historia);
        habito.setTipo(dto.tipo());
        habito.setFrecuencia(dto.frecuencia());
        habito.setDetalle(dto.detalle() != null ? dto.detalle() : "");

        Habito saved = habitoRepo.save(habito);
        return HabitoMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public HabitoResponse obtenerPorId(UUID id) {
        Habito habito = habitoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hábito no encontrado: " + id));
        return HabitoMapper.toResponse(habito);
    }

    @Transactional(readOnly = true)
    public List<HabitoResponse> listarPorHistoria(UUID historiaClinicaId) {
        List<Habito> habitos = habitoRepo.findByHistoriaClinicaId(historiaClinicaId);
        return habitos.stream()
                .map(HabitoMapper::toResponse)
                .toList();
    }

    @Transactional
    public HabitoResponse actualizar(UUID id, UpdateHabitoDto dto) {
        Habito habito = habitoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hábito no encontrado: " + id));

        if (dto.frecuencia() != null) habito.setFrecuencia(dto.frecuencia());
        if (dto.detalle() != null) habito.setDetalle(dto.detalle());

        Habito updated = habitoRepo.save(habito);
        return HabitoMapper.toResponse(updated);
    }

    @Transactional
    public void eliminar(UUID id) {
        Habito habito = habitoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hábito no encontrado: " + id));
        habitoRepo.delete(habito);
    }
}
