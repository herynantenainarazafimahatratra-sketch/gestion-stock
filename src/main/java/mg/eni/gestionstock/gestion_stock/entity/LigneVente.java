package mg.eni.gestionstock.gestion_stock.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ligne_vente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LigneVente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantite;

    @Column(nullable = false)
    private BigDecimal prixUnitaire;

    @ManyToOne
    @JoinColumn(name = "vente_id", nullable = false)
    @JsonBackReference
    private Vente vente;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;
}