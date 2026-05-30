package org.inspire.backend.modules.contactoemergencia;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.modules.contactoemergencia.dto.ContactoEmergenciaResponse;
import org.inspire.backend.modules.contactoemergencia.dto.CreateContactoEmergenciaDto;
import org.inspire.backend.modules.contactoemergencia.dto.UpdateContactoEmergenciaDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pacientes/{pacienteId}/contactos-emergencia")
@RequiredArgsConstructor
public class ContactoEmergenciaController {

    private final ContactoEmergenciaService contactoService;

    @PostMapping
    public ResponseEntity<ContactoEmergenciaResponse> crear(
            @PathVariable UUID pacienteId,
            @Valid @RequestBody CreateContactoEmergenciaDto dto) {
        ContactoEmergenciaResponse response = contactoService.crear(pacienteId, dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ContactoEmergenciaResponse>> listarPorPaciente(@PathVariable UUID pacienteId) {
        return ResponseEntity.ok(contactoService.listarPorPaciente(pacienteId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactoEmergenciaResponse> obtenerPorId(
            @PathVariable UUID pacienteId,
            @PathVariable UUID id) {
        return ResponseEntity.ok(contactoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactoEmergenciaResponse> actualizar(
            @PathVariable UUID pacienteId,
            @PathVariable UUID id,
            @Valid @RequestBody UpdateContactoEmergenciaDto dto) {
        return ResponseEntity.ok(contactoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable UUID pacienteId,
            @PathVariable UUID id) {
        contactoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
