
package mg.eni.gestionstock.gestion_stock.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import mg.eni.gestionstock.gestion_stock.enums.StatutCommande;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bon_commande")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BonCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dateCommande;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCommande statut;

    @ManyToOne
    @JoinColumn(name = "fournisseur_id", nullable = false)
    private Fournisseur fournisseur;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "bonCommande", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<LigneCommande> lignes;
}
