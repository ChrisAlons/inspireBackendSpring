package org.inspire.backend.modules.cita;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.modules.auth.UsuarioPrincipal;
import org.inspire.backend.modules.cita.dto.CambiarEstadoCitaDto;
import org.inspire.backend.modules.cita.dto.CitaResponse;
import org.inspire.backend.modules.cita.dto.CreateCitaDto;
import org.inspire.backend.modules.cita.dto.UpdateCitaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @PostMapping
    public ResponseEntity<CitaResponse> crear(@Valid @RequestBody CreateCitaDto dto) {
        CitaResponse response = citaService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponse> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(citaService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<CitaResponse>> buscar(
            @RequestParam(required = false) UUID pacienteId,
            Pageable pageable,
            @AuthenticationPrincipal UsuarioPrincipal principal) {
        return ResponseEntity.ok(citaService.buscar(pacienteId, pageable, principal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCitaDto dto) {
        return ResponseEntity.ok(citaService.actualizar(id, dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<CitaResponse> cambiarEstado(
            @PathVariable UUID id,
            @Valid @RequestBody CambiarEstadoCitaDto dto) {
        return ResponseEntity.ok(citaService.cambiarEstado(id, dto.estado()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
