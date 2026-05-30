package org.inspire.backend.modules.enfermedadactual;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.modules.enfermedadactual.dto.CreateEnfermedadActualDto;
import org.inspire.backend.modules.enfermedadactual.dto.EnfermedadActualResponse;
import org.inspire.backend.modules.enfermedadactual.dto.UpdateEnfermedadActualDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/enfermedades-actuales")
@RequiredArgsConstructor
public class EnfermedadActualController {

    private final EnfermedadActualService enfermedadService;

    @PostMapping
    public ResponseEntity<EnfermedadActualResponse> crear(@Valid @RequestBody CreateEnfermedadActualDto dto) {
        EnfermedadActualResponse response = enfermedadService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnfermedadActualResponse> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(enfermedadService.obtenerPorId(id));
    }

    @GetMapping("/por-atencion/{atencionId}")
    public ResponseEntity<EnfermedadActualResponse> obtenerPorAtencion(@PathVariable UUID atencionId) {
        return ResponseEntity.ok(enfermedadService.obtenerPorAtencion(atencionId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnfermedadActualResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateEnfermedadActualDto dto) {
        return ResponseEntity.ok(enfermedadService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        enfermedadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
