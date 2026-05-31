package org.inspire.backend.modules.odontograma;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.catalogo.caradental.CaraDental;
import org.inspire.backend.catalogo.caradental.CaraDentalRepository;
import org.inspire.backend.catalogo.condiciondental.CondicionDental;
import org.inspire.backend.catalogo.condiciondental.CondicionDentalRepository;
import org.inspire.backend.catalogo.piezadental.PiezaDental;
import org.inspire.backend.catalogo.piezadental.PiezaDentalRepository;
import org.inspire.backend.common.enums.EstadoHallazgo;
import org.inspire.backend.common.exception.BusinessException;
import org.inspire.backend.common.exception.ResourceNotFoundException;
import org.inspire.backend.modules.odontograma.dto.CreateHallazgoDto;
import org.inspire.backend.modules.odontograma.dto.CreateHallazgoVozDto;
import org.inspire.backend.modules.odontograma.dto.HallazgoResponse;
import org.inspire.backend.modules.odontograma.dto.UpdateHallazgoDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class OdontogramaHallazgoService {

    private final OdontogramaHallazgoRepository hallazgoRepo;
    private final OdontogramaRepository odontogramaRepo;
    private final PiezaDentalRepository piezaRepo;
    private final CaraDentalRepository caraRepo;
    private final CondicionDentalRepository condicionRepo;

    @Transactional
    public HallazgoResponse crear(UUID odontogramaId, @Valid CreateHallazgoDto dto) {
        Odontograma odontograma = odontogramaRepo.findById(odontogramaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Odontograma no encontrado: " + odontogramaId));

        PiezaDental pieza = piezaRepo.findById(dto.piezaId().shortValue())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pieza dental no encontrada: " + dto.piezaId()));

        CaraDental cara = caraRepo.findByCodigo(dto.caraCodigo())
                .orElseThrow(() -> new BusinessException(
                        "Cara dental no encontrada: " + dto.caraCodigo()));

        CondicionDental condicion = condicionRepo.findByCodigo(dto.condicionCodigo())
                .orElseThrow(() -> new BusinessException(
                        "Condición dental no encontrada: " + dto.condicionCodigo()));

        OdontogramaHallazgo hallazgo = new OdontogramaHallazgo();
        hallazgo.setOdontograma(odontograma);
        hallazgo.setPieza(pieza);
        hallazgo.setCara(cara);
        hallazgo.setCondicion(condicion);
        hallazgo.setEstado(dto.estado() != null ? dto.estado() : EstadoHallazgo.EXISTENTE);
        hallazgo.setNotas(dto.notas() != null ? dto.notas() : "");

        OdontogramaHallazgo saved = hallazgoRepo.save(hallazgo);
        return OdontogramaHallazgoMapper.toResponse(saved);
    }

    @Transactional
    public List<HallazgoResponse> crearPorVoz(UUID odontogramaId, @Valid CreateHallazgoVozDto dto) {
        Odontograma odontograma = odontogramaRepo.findById(odontogramaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Odontograma no encontrado: " + odontogramaId));

        List<OdontogramaHallazgo> hallazgosCreados = new ArrayList<>();

        for (CreateHallazgoDto h : dto.hallazgos()) {
            PiezaDental pieza = piezaRepo.findById(h.piezaId().shortValue())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Pieza dental no encontrada: " + h.piezaId()));

            CaraDental cara = caraRepo.findByCodigo(h.caraCodigo())
                    .orElseThrow(() -> new BusinessException(
                            "Cara dental no encontrada: " + h.caraCodigo()));

            CondicionDental condicion = condicionRepo.findByCodigo(h.condicionCodigo())
                    .orElseThrow(() -> new BusinessException(
                            "Condición dental no encontrada: " + h.condicionCodigo()));

            OdontogramaHallazgo hallazgo = new OdontogramaHallazgo();
            hallazgo.setOdontograma(odontograma);
            hallazgo.setPieza(pieza);
            hallazgo.setCara(cara);
            hallazgo.setCondicion(condicion);
            hallazgo.setEstado(h.estado() != null ? h.estado() : EstadoHallazgo.EXISTENTE);
            hallazgo.setNotas(h.notas() != null ? h.notas() : "");
            hallazgo.setRegistradoVoz(true);
            hallazgo.setTranscripcionVoz(dto.transcripcionVoz());

            hallazgosCreados.add(hallazgoRepo.save(hallazgo));
        }

        return hallazgosCreados.stream()
                .map(OdontogramaHallazgoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public HallazgoResponse obtenerPorId(UUID id) {
        OdontogramaHallazgo hallazgo = hallazgoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hallazgo no encontrado: " + id));
        return OdontogramaHallazgoMapper.toResponse(hallazgo);
    }

    @Transactional(readOnly = true)
    public List<HallazgoResponse> listarPorOdontograma(UUID odontogramaId) {
        List<OdontogramaHallazgo> hallazgos = hallazgoRepo.findByOdontogramaId(odontogramaId);
        return hallazgos.stream()
                .map(OdontogramaHallazgoMapper::toResponse)
                .toList();
    }

    @Transactional
    public HallazgoResponse actualizar(UUID id, UpdateHallazgoDto dto) {
        OdontogramaHallazgo hallazgo = hallazgoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hallazgo no encontrado: " + id));

        if (dto.estado() != null) hallazgo.setEstado(dto.estado());
        if (dto.notas() != null) hallazgo.setNotas(dto.notas());

        OdontogramaHallazgo updated = hallazgoRepo.save(hallazgo);
        return OdontogramaHallazgoMapper.toResponse(updated);
    }

    @Transactional
    public void eliminar(UUID id) {
        OdontogramaHallazgo hallazgo = hallazgoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hallazgo no encontrado: " + id));
        hallazgoRepo.delete(hallazgo);
    }
}
