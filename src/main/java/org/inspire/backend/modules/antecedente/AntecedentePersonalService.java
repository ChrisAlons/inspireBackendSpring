package org.inspire.backend.modules.antecedente;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.common.exception.ResourceNotFoundException;
import org.inspire.backend.modules.antecedente.dto.AntecedentePersonalResponse;
import org.inspire.backend.modules.antecedente.dto.CreateAntecedentePersonalDto;
import org.inspire.backend.modules.antecedente.dto.UpdateAntecedentePersonalDto;
import org.inspire.backend.modules.historiaclinica.HistoriaClinica;
import org.inspire.backend.modules.historiaclinica.HistoriaClinicaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class AntecedentePersonalService {

    private final AntecedentePersonalRepository antecedenteRepo;
    private final HistoriaClinicaRepository historiaRepo;

    @Transactional
    public AntecedentePersonalResponse crear(@Valid CreateAntecedentePersonalDto dto) {
        HistoriaClinica historia = historiaRepo.findById(dto.historiaClinicaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historia clínica no encontrada: " + dto.historiaClinicaId()));

        AntecedentePersonal antecedente = new AntecedentePersonal();
        antecedente.setHistoriaClinica(historia);
        antecedente.setDescripcion(dto.descripcion());
        antecedente.setFechaRegistro(dto.fechaRegistro() != null ? dto.fechaRegistro() : LocalDate.now());

        AntecedentePersonal saved = antecedenteRepo.save(antecedente);
        return AntecedentePersonalMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public AntecedentePersonalResponse obtenerPorId(UUID id) {
        AntecedentePersonal antecedente = antecedenteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Antecedente personal no encontrado: " + id));
        return AntecedentePersonalMapper.toResponse(antecedente);
    }

    @Transactional(readOnly = true)
    public List<AntecedentePersonalResponse> listarPorHistoria(UUID historiaClinicaId) {
        List<AntecedentePersonal> antecedentes = antecedenteRepo.findByHistoriaClinicaIdOrderByFechaRegistroDesc(historiaClinicaId);
        return antecedentes.stream()
                .map(AntecedentePersonalMapper::toResponse)
                .toList();
    }

    @Transactional
    public AntecedentePersonalResponse actualizar(UUID id, UpdateAntecedentePersonalDto dto) {
        AntecedentePersonal antecedente = antecedenteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Antecedente personal no encontrado: " + id));

        if (dto.descripcion() != null) antecedente.setDescripcion(dto.descripcion());
        if (dto.fechaRegistro() != null) antecedente.setFechaRegistro(dto.fechaRegistro());

        AntecedentePersonal updated = antecedenteRepo.save(antecedente);
        return AntecedentePersonalMapper.toResponse(updated);
    }

    @Transactional
    public void eliminar(UUID id) {
        AntecedentePersonal antecedente = antecedenteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Antecedente personal no encontrado: " + id));
        antecedenteRepo.delete(antecedente);
    }
}
