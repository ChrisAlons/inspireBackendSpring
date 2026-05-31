package org.inspire.backend.modules.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.modules.auth.dto.LoginRequest;
import org.inspire.backend.modules.auth.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
