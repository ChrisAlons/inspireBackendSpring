package org.inspire.backend.modules.persona;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.modules.persona.dto.CreatePersonaDto;
import org.inspire.backend.modules.persona.dto.PersonaResponse;
import org.inspire.backend.modules.persona.dto.UpdatePersonaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;

    @PostMapping
    public ResponseEntity<PersonaResponse> crear(@Valid @RequestBody CreatePersonaDto dto) {
        PersonaResponse response = personaService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaResponse> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(personaService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<PersonaResponse>> buscar(
            @RequestParam(required = false) String q,
            Pageable pageable) {
        return ResponseEntity.ok(personaService.buscar(q, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePersonaDto dto) {
        return ResponseEntity.ok(personaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        personaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
