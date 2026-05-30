package org.inspire.backend.modules.historiaclinica;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.modules.historiaclinica.dto.CreateHistoriaClinicaDto;
import org.inspire.backend.modules.historiaclinica.dto.HistoriaClinicaResponse;
import org.inspire.backend.modules.historiaclinica.dto.UpdateHistoriaClinicaDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/historias-clinicas")
@RequiredArgsConstructor
public class HistoriaClinicaController {

    private final HistoriaClinicaService historiaService;

    @PostMapping
    public ResponseEntity<HistoriaClinicaResponse> crear(@Valid @RequestBody CreateHistoriaClinicaDto dto) {
        HistoriaClinicaResponse response = historiaService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriaClinicaResponse> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(historiaService.obtenerPorId(id));
    }

    @GetMapping("/por-paciente/{pacienteId}")
    public ResponseEntity<HistoriaClinicaResponse> obtenerPorPaciente(@PathVariable UUID pacienteId) {
        return ResponseEntity.ok(historiaService.obtenerPorPaciente(pacienteId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoriaClinicaResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateHistoriaClinicaDto dto) {
        return ResponseEntity.ok(historiaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        historiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
