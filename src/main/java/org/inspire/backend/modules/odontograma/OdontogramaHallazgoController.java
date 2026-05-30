package org.inspire.backend.modules.odontograma;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.modules.odontograma.dto.CreateHallazgoDto;
import org.inspire.backend.modules.odontograma.dto.CreateHallazgoVozDto;
import org.inspire.backend.modules.odontograma.dto.HallazgoResponse;
import org.inspire.backend.modules.odontograma.dto.UpdateHallazgoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/odontogramas/{odontogramaId}/hallazgos")
@RequiredArgsConstructor
public class OdontogramaHallazgoController {

    private final OdontogramaHallazgoService hallazgoService;

    @PostMapping
    public ResponseEntity<HallazgoResponse> crear(
            @PathVariable UUID odontogramaId,
            @Valid @RequestBody CreateHallazgoDto dto) {
        HallazgoResponse response = hallazgoService.crear(odontogramaId, dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/voz")
    public ResponseEntity<List<HallazgoResponse>> crearPorVoz(
            @PathVariable UUID odontogramaId,
            @Valid @RequestBody CreateHallazgoVozDto dto) {
        List<HallazgoResponse> responses = hallazgoService.crearPorVoz(odontogramaId, dto);
        return ResponseEntity.status(201).body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HallazgoResponse> obtenerPorId(
            @PathVariable UUID odontogramaId,
            @PathVariable UUID id) {
        return ResponseEntity.ok(hallazgoService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<HallazgoResponse>> listarPorOdontograma(@PathVariable UUID odontogramaId) {
        return ResponseEntity.ok(hallazgoService.listarPorOdontograma(odontogramaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HallazgoResponse> actualizar(
            @PathVariable UUID odontogramaId,
            @PathVariable UUID id,
            @Valid @RequestBody UpdateHallazgoDto dto) {
        return ResponseEntity.ok(hallazgoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable UUID odontogramaId,
            @PathVariable UUID id) {
        hallazgoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
