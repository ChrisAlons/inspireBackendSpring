package org.inspire.backend.modules.plantratamiento;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.modules.plantratamiento.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/planes-tratamiento")
@RequiredArgsConstructor
public class PlanTratamientoController {

    private final PlanTratamientoService planService;

    @PostMapping
    public ResponseEntity<PlanTratamientoResponse> crear(@Valid @RequestBody CreatePlanTratamientoDto dto) {
        PlanTratamientoResponse response = planService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanTratamientoResponse> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(planService.obtenerPorId(id));
    }

    @GetMapping("/por-historia/{historiaClinicaId}")
    public ResponseEntity<List<PlanTratamientoResponse>> listarPorHistoria(@PathVariable UUID historiaClinicaId) {
        return ResponseEntity.ok(planService.listarPorHistoria(historiaClinicaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanTratamientoResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePlanTratamientoDto dto) {
        return ResponseEntity.ok(planService.actualizar(id, dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PlanTratamientoResponse> cambiarEstado(
            @PathVariable UUID id,
            @Valid @RequestBody CambiarEstadoPlanDto dto) {
        return ResponseEntity.ok(planService.cambiarEstado(id, dto));
    }

    @GetMapping("/{id}/eventos")
    public ResponseEntity<List<PlanEventoResponse>> listarEventos(@PathVariable UUID id) {
        return ResponseEntity.ok(planService.listarEventos(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        planService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
