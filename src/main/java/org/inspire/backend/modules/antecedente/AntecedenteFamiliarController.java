package org.inspire.backend.modules.antecedente;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.modules.antecedente.dto.AntecedenteFamiliarResponse;
import org.inspire.backend.modules.antecedente.dto.CreateAntecedenteFamiliarDto;
import org.inspire.backend.modules.antecedente.dto.UpdateAntecedenteFamiliarDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/antecedentes-familiares")
@RequiredArgsConstructor
public class AntecedenteFamiliarController {

    private final AntecedenteFamiliarService antecedenteService;

    @PostMapping
    public ResponseEntity<AntecedenteFamiliarResponse> crear(@Valid @RequestBody CreateAntecedenteFamiliarDto dto) {
        AntecedenteFamiliarResponse response = antecedenteService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AntecedenteFamiliarResponse> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(antecedenteService.obtenerPorId(id));
    }

    @GetMapping("/por-historia/{historiaClinicaId}")
    public ResponseEntity<List<AntecedenteFamiliarResponse>> listarPorHistoria(@PathVariable UUID historiaClinicaId) {
        return ResponseEntity.ok(antecedenteService.listarPorHistoria(historiaClinicaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AntecedenteFamiliarResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAntecedenteFamiliarDto dto) {
        return ResponseEntity.ok(antecedenteService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        antecedenteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
