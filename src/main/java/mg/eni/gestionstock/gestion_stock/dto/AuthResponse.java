package mg.eni.gestionstock.gestion_stock.dto;

public record AuthResponse(String token, String nom, String email, String role) {}