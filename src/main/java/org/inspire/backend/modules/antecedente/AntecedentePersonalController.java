package org.inspire.backend.modules.antecedente;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.modules.antecedente.dto.AntecedentePersonalResponse;
import org.inspire.backend.modules.antecedente.dto.CreateAntecedentePersonalDto;
import org.inspire.backend.modules.antecedente.dto.UpdateAntecedentePersonalDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/antecedentes-personales")
@RequiredArgsConstructor
public class AntecedentePersonalController {

    private final AntecedentePersonalService antecedenteService;

    @PostMapping
    public ResponseEntity<AntecedentePersonalResponse> crear(@Valid @RequestBody CreateAntecedentePersonalDto dto) {
        AntecedentePersonalResponse response = antecedenteService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AntecedentePersonalResponse> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(antecedenteService.obtenerPorId(id));
    }

    @GetMapping("/por-historia/{historiaClinicaId}")
    public ResponseEntity<List<AntecedentePersonalResponse>> listarPorHistoria(@PathVariable UUID historiaClinicaId) {
        return ResponseEntity.ok(antecedenteService.listarPorHistoria(historiaClinicaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AntecedentePersonalResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAntecedentePersonalDto dto) {
        return ResponseEntity.ok(antecedenteService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        antecedenteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
