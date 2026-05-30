package org.inspire.backend.modules.antecedente;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.catalogo.parentesco.Parentesco;
import org.inspire.backend.catalogo.parentesco.ParentescoRepository;
import org.inspire.backend.common.exception.BusinessException;
import org.inspire.backend.common.exception.ResourceNotFoundException;
import org.inspire.backend.modules.antecedente.dto.AntecedenteFamiliarResponse;
import org.inspire.backend.modules.antecedente.dto.CreateAntecedenteFamiliarDto;
import org.inspire.backend.modules.antecedente.dto.UpdateAntecedenteFamiliarDto;
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
public class AntecedenteFamiliarService {

    private final AntecedenteFamiliarRepository antecedenteRepo;
    private final HistoriaClinicaRepository historiaRepo;
    private final ParentescoRepository parentescoRepo;

    @Transactional
    public AntecedenteFamiliarResponse crear(@Valid CreateAntecedenteFamiliarDto dto) {
        HistoriaClinica historia = historiaRepo.findById(dto.historiaClinicaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historia clínica no encontrada: " + dto.historiaClinicaId()));

        Parentesco parentesco = parentescoRepo.findByCodigo(dto.parentescoCodigo())
                .orElseThrow(() -> new BusinessException(
                        "Parentesco no encontrado: " + dto.parentescoCodigo()));

        AntecedenteFamiliar antecedente = new AntecedenteFamiliar();
        antecedente.setHistoriaClinica(historia);
        antecedente.setParentesco(parentesco);
        antecedente.setDescripcion(dto.descripcion());

        AntecedenteFamiliar saved = antecedenteRepo.save(antecedente);
        return AntecedenteFamiliarMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public AntecedenteFamiliarResponse obtenerPorId(UUID id) {
        AntecedenteFamiliar antecedente = antecedenteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Antecedente familiar no encontrado: " + id));
        return AntecedenteFamiliarMapper.toResponse(antecedente);
    }

    @Transactional(readOnly = true)
    public List<AntecedenteFamiliarResponse> listarPorHistoria(UUID historiaClinicaId) {
        List<AntecedenteFamiliar> antecedentes = antecedenteRepo.findByHistoriaClinicaId(historiaClinicaId);
        return antecedentes.stream()
                .map(AntecedenteFamiliarMapper::toResponse)
                .toList();
    }

    @Transactional
    public AntecedenteFamiliarResponse actualizar(UUID id, UpdateAntecedenteFamiliarDto dto) {
        AntecedenteFamiliar antecedente = antecedenteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Antecedente familiar no encontrado: " + id));

        if (dto.descripcion() != null) antecedente.setDescripcion(dto.descripcion());

        if (dto.parentescoCodigo() != null) {
            Parentesco parentesco = parentescoRepo.findByCodigo(dto.parentescoCodigo())
                    .orElseThrow(() -> new BusinessException(
                            "Parentesco no encontrado: " + dto.parentescoCodigo()));
            antecedente.setParentesco(parentesco);
        }

        AntecedenteFamiliar updated = antecedenteRepo.save(antecedente);
        return AntecedenteFamiliarMapper.toResponse(updated);
    }

    @Transactional
    public void eliminar(UUID id) {
        AntecedenteFamiliar antecedente = antecedenteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Antecedente familiar no encontrado: " + id));
        antecedenteRepo.delete(antecedente);
    }
}
