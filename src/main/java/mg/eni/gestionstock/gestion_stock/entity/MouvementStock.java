package mg.eni.gestionstock.gestion_stock.entity;
import jakarta.persistence.*;
import lombok.*;
import mg.eni.gestionstock.gestion_stock.enums.TypeMouvement;

import java.time.LocalDateTime;

@Entity
@Table(name = "mouvement_stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MouvementStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeMouvement type;

    @Column(nullable = false)
    private Integer quantite;

    @Column(nullable = false)
    private LocalDateTime dateMouvement;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;
}