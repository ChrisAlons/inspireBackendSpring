package org.inspire.backend.modules.auth.dto;

import java.util.UUID;

public record LoginResponse(
        String token,
        UUID usuarioId,
        UUID personaId,
        String email,
        String nombres,
        String apellidoPaterno
) {}
