package mg.eni.gestionstock.gestion_stock.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String reference;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private BigDecimal prixAchat;

    @Column(nullable = false)
    private BigDecimal prixVente;

    @Column(nullable = false)
    private Integer quantiteStock;

    @Column(nullable = false)
    private Integer seuilAlerte;

    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;
}