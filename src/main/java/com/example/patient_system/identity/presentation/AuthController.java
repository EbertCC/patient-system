package com.example.patient_system.identity.presentation;

import com.example.patient_system.identity.domain.model.UserAccount;
import com.example.patient_system.identity.domain.repository.UserAccountRepository;
import com.example.patient_system.identity.domain.service.PasswordHasher;
import com.example.patient_system.identity.infrastructure.security.JwtService;
import com.example.patient_system.identity.presentation.dto.LoginRequest;
import com.example.patient_system.identity.presentation.dto.LoginResponse;
import com.example.patient_system.shared.domain.Email;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserAccountRepository accounts;
    private final PasswordHasher passwordHasher;
    private final JwtService jwtService;

    public AuthController(UserAccountRepository accounts, PasswordHasher passwordHasher, JwtService jwtService) {
        this.accounts = accounts;
        this.passwordHasher = passwordHasher;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        UserAccount account = accounts.findByEmail(Email.of(request.getEmail()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales invalidas"));

        if (!passwordHasher.matches(request.getPassword(), account.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales invalidas");
        }

        String token = jwtService.generateToken(account.email().value());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
