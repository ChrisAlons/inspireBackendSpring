package org.inspire.backend.modules.paciente;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.modules.paciente.dto.CreatePacienteDto;
import org.inspire.backend.modules.paciente.dto.PacienteResponse;
import org.inspire.backend.modules.paciente.dto.UpdatePacienteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<PacienteResponse> crear(@Valid @RequestBody CreatePacienteDto dto) {
        PacienteResponse response = pacienteService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(pacienteService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<PacienteResponse>> buscar(
            @RequestParam(required = false) String q,
            Pageable pageable) {
        return ResponseEntity.ok(pacienteService.buscar(q, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePacienteDto dto) {
        return ResponseEntity.ok(pacienteService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        pacienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
