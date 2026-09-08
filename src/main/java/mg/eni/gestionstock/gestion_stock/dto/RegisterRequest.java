package mg.eni.gestionstock.gestion_stock.dto;

import mg.eni.gestionstock.gestion_stock.enums.Role;

public record RegisterRequest(String nom, String email, String motDePasse, Role role) {}