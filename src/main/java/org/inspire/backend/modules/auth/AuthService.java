package org.inspire.backend.modules.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.common.exception.BusinessException;
import org.inspire.backend.modules.auth.dto.LoginRequest;
import org.inspire.backend.modules.auth.dto.LoginResponse;
import org.inspire.backend.modules.persona.Persona;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginResponse login(@Valid LoginRequest request) {
        Usuario usuario = usuarioRepo.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException("Credenciales inválidas"));

        if (!usuario.isActive()) {
            throw new BusinessException("Usuario desactivado");
        }

        if (!passwordEncoder.matches(request.password(), usuario.getPasswordHash())) {
            throw new BusinessException("Credenciales inválidas");
        }

        Persona persona = usuario.getPersona();
        String token = jwtService.generateToken(usuario.getId(), persona.getId(), usuario.getEmail());

        return new LoginResponse(
                token,
                usuario.getId(),
                persona.getId(),
                usuario.getEmail(),
                persona.getNombres(),
                persona.getApellidoPaterno()
        );
    }
}
