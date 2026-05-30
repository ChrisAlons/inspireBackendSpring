package org.inspire.backend.modules.tratamientoejecutado;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.modules.tratamientoejecutado.dto.CreateTratamientoEjecutadoDto;
import org.inspire.backend.modules.tratamientoejecutado.dto.TratamientoEjecutadoResponse;
import org.inspire.backend.modules.tratamientoejecutado.dto.UpdateTratamientoEjecutadoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tratamientos-ejecutados")
@RequiredArgsConstructor
public class TratamientoEjecutadoController {

    private final TratamientoEjecutadoService tratamientoService;

    @PostMapping
    public ResponseEntity<TratamientoEjecutadoResponse> crear(@Valid @RequestBody CreateTratamientoEjecutadoDto dto) {
        TratamientoEjecutadoResponse response = tratamientoService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TratamientoEjecutadoResponse> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(tratamientoService.obtenerPorId(id));
    }

    @GetMapping("/por-atencion/{atencionId}")
    public ResponseEntity<List<TratamientoEjecutadoResponse>> listarPorAtencion(@PathVariable UUID atencionId) {
        return ResponseEntity.ok(tratamientoService.listarPorAtencion(atencionId));
    }

    @GetMapping("/por-detalle/{planDetalleId}")
    public ResponseEntity<List<TratamientoEjecutadoResponse>> listarPorDetalle(@PathVariable UUID planDetalleId) {
        return ResponseEntity.ok(tratamientoService.listarPorDetalle(planDetalleId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TratamientoEjecutadoResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTratamientoEjecutadoDto dto) {
        return ResponseEntity.ok(tratamientoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        tratamientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
