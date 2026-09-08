package mg.eni.gestionstock.gestion_stock.controller;

import mg.eni.gestionstock.gestion_stock.dto.AuthResponse;
import mg.eni.gestionstock.gestion_stock.dto.LoginRequest;
import mg.eni.gestionstock.gestion_stock.dto.RegisterRequest;
import mg.eni.gestionstock.gestion_stock.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}