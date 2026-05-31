package org.inspire.backend.modules.atencion;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.modules.atencion.dto.AtencionResponse;
import org.inspire.backend.modules.atencion.dto.CreateAtencionDto;
import org.inspire.backend.modules.atencion.dto.UpdateAtencionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/atenciones")
@RequiredArgsConstructor
public class AtencionController {

    private final AtencionService atencionService;

    @PostMapping
    public ResponseEntity<AtencionResponse> crear(@Valid @RequestBody CreateAtencionDto dto) {
        AtencionResponse response = atencionService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtencionResponse> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(atencionService.obtenerPorId(id));
    }

    @GetMapping("/por-historia/{historiaClinicaId}")
    public ResponseEntity<List<AtencionResponse>> listarPorHistoria(@PathVariable UUID historiaClinicaId) {
        return ResponseEntity.ok(atencionService.listarPorHistoria(historiaClinicaId));
    }

    @GetMapping("/porcita/{citaId}")
    public ResponseEntity<AtencionResponse> obtenerPorCita(@PathVariable UUID citaId) {
        return ResponseEntity.ok(atencionService.obtenerPorCitaId(citaId));
    }

    @GetMapping("/en-curso")
    public ResponseEntity<List<AtencionResponse>> listarEnCurso() {
        return ResponseEntity.ok(atencionService.listarEnCurso());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtencionResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAtencionDto dto) {
        return ResponseEntity.ok(atencionService.actualizar(id, dto));
    }

    @PatchMapping("/{id}/cerrar")
    public ResponseEntity<AtencionResponse> cerrar(@PathVariable UUID id) {
        return ResponseEntity.ok(atencionService.cerrar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        atencionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
