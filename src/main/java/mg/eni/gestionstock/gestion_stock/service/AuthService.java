package mg.eni.gestionstock.gestion_stock.service;

import mg.eni.gestionstock.gestion_stock.dto.AuthResponse;
import mg.eni.gestionstock.gestion_stock.dto.LoginRequest;
import mg.eni.gestionstock.gestion_stock.dto.RegisterRequest;
import mg.eni.gestionstock.gestion_stock.entity.Utilisateur;
import mg.eni.gestionstock.gestion_stock.repository.UtilisateurRepository;
import mg.eni.gestionstock.gestion_stock.security.CustomUserDetails;
import mg.eni.gestionstock.gestion_stock.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (utilisateurRepository.existsByEmail(request.email())) {
            throw new IllegalStateException("Un utilisateur avec cet email existe déjà");
        }

        Utilisateur utilisateur = Utilisateur.builder()
                .nom(request.nom())
                .email(request.email())
                .motDePasse(passwordEncoder.encode(request.motDePasse()))
                .role(request.role())
                .build();

        utilisateurRepository.save(utilisateur);

        CustomUserDetails userDetails = new CustomUserDetails(utilisateur);
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, utilisateur.getNom(), utilisateur.getEmail(), utilisateur.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.motDePasse())
        );

        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalStateException("Utilisateur introuvable"));

        CustomUserDetails userDetails = new CustomUserDetails(utilisateur);
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, utilisateur.getNom(), utilisateur.getEmail(), utilisateur.getRole().name());
    }
}