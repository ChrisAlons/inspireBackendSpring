package org.inspire.backend.modules.habito;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.modules.habito.dto.CreateHabitoDto;
import org.inspire.backend.modules.habito.dto.HabitoResponse;
import org.inspire.backend.modules.habito.dto.UpdateHabitoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/habitos")
@RequiredArgsConstructor
public class HabitoController {

    private final HabitoService habitoService;

    @PostMapping
    public ResponseEntity<HabitoResponse> crear(@Valid @RequestBody CreateHabitoDto dto) {
        HabitoResponse response = habitoService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitoResponse> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(habitoService.obtenerPorId(id));
    }

    @GetMapping("/por-historia/{historiaClinicaId}")
    public ResponseEntity<List<HabitoResponse>> listarPorHistoria(@PathVariable UUID historiaClinicaId) {
        return ResponseEntity.ok(habitoService.listarPorHistoria(historiaClinicaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitoResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateHabitoDto dto) {
        return ResponseEntity.ok(habitoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        habitoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
