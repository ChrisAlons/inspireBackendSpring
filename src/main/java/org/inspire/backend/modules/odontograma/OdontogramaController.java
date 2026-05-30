package org.inspire.backend.modules.odontograma;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.modules.odontograma.dto.CreateOdontogramaDto;
import org.inspire.backend.modules.odontograma.dto.OdontogramaResponse;
import org.inspire.backend.modules.odontograma.dto.UpdateOdontogramaDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/odontogramas")
@RequiredArgsConstructor
public class OdontogramaController {

    private final OdontogramaService odontogramaService;

    @PostMapping
    public ResponseEntity<OdontogramaResponse> crear(@Valid @RequestBody CreateOdontogramaDto dto) {
        OdontogramaResponse response = odontogramaService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontogramaResponse> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(odontogramaService.obtenerPorId(id));
    }

    @GetMapping("/por-historia/{historiaClinicaId}")
    public ResponseEntity<List<OdontogramaResponse>> listarPorHistoria(@PathVariable UUID historiaClinicaId) {
        return ResponseEntity.ok(odontogramaService.listarPorHistoria(historiaClinicaId));
    }

    @GetMapping("/por-atencion/{atencionId}")
    public ResponseEntity<List<OdontogramaResponse>> listarPorAtencion(@PathVariable UUID atencionId) {
        return ResponseEntity.ok(odontogramaService.listarPorAtencion(atencionId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OdontogramaResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateOdontogramaDto dto) {
        return ResponseEntity.ok(odontogramaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        odontogramaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
