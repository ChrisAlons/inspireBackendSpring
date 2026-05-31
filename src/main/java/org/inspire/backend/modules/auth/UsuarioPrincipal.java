package org.inspire.backend.modules.auth;

import java.security.Principal;
import java.util.UUID;

public record UsuarioPrincipal(
        UUID usuarioId,
        UUID personaId,
        String email
) implements Principal {
    @Override
    public String getName() {
        return email;
    }
}
