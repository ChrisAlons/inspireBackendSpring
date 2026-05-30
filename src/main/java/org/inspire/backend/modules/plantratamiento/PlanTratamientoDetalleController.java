package org.inspire.backend.modules.plantratamiento;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.modules.plantratamiento.dto.CreatePlanDetalleDto;
import org.inspire.backend.modules.plantratamiento.dto.PlanDetalleResponse;
import org.inspire.backend.modules.plantratamiento.dto.UpdatePlanDetalleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/planes-tratamiento/{planId}/detalles")
@RequiredArgsConstructor
public class PlanTratamientoDetalleController {

    private final PlanTratamientoDetalleService detalleService;

    @PostMapping
    public ResponseEntity<PlanDetalleResponse> crear(
            @PathVariable UUID planId,
            @Valid @RequestBody CreatePlanDetalleDto dto) {
        PlanDetalleResponse response = detalleService.crear(planId, dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanDetalleResponse> obtenerPorId(
            @PathVariable UUID planId,
            @PathVariable UUID id) {
        return ResponseEntity.ok(detalleService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<PlanDetalleResponse>> listarPorPlan(@PathVariable UUID planId) {
        return ResponseEntity.ok(detalleService.listarPorPlan(planId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanDetalleResponse> actualizar(
            @PathVariable UUID planId,
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePlanDetalleDto dto) {
        return ResponseEntity.ok(detalleService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable UUID planId,
            @PathVariable UUID id) {
        detalleService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
