package mg.eni.gestionstock.gestion_stock.service;

import mg.eni.gestionstock.gestion_stock.entity.MouvementStock;
import mg.eni.gestionstock.gestion_stock.entity.Produit;
import mg.eni.gestionstock.gestion_stock.entity.Utilisateur;
import mg.eni.gestionstock.gestion_stock.enums.TypeMouvement;
import mg.eni.gestionstock.gestion_stock.repository.MouvementStockRepository;
import mg.eni.gestionstock.gestion_stock.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StockService {

    private final ProduitRepository produitRepository;
    private final MouvementStockRepository mouvementStockRepository;

    /**
     * Enregistre une entrée de stock (réception fournisseur)
     * et augmente la quantité en stock du produit.
     */
    @Transactional
    public void entreeStock(Long produitId, Integer quantite, Utilisateur utilisateur) {
        if (quantite == null || quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être positive");
        }

        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit introuvable avec l'id : " + produitId));

        produit.setQuantiteStock(produit.getQuantiteStock() + quantite);
        produitRepository.save(produit);

        MouvementStock mouvement = MouvementStock.builder()
                .type(TypeMouvement.ENTREE)
                .quantite(quantite)
                .dateMouvement(LocalDateTime.now())
                .produit(produit)
                .utilisateur(utilisateur)
                .build();

        mouvementStockRepository.save(mouvement);
    }

    /**
     * Enregistre une sortie de stock (vente)
     * et diminue la quantité en stock du produit.
     * Lève une exception si le stock est insuffisant.
     */
    @Transactional
    public void sortieStock(Long produitId, Integer quantite, Utilisateur utilisateur) {
        if (quantite == null || quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être positive");
        }

        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit introuvable avec l'id : " + produitId));

        if (produit.getQuantiteStock() < quantite) {
            throw new IllegalStateException(
                    "Stock insuffisant pour le produit '" + produit.getNom() +
                            "' (disponible : " + produit.getQuantiteStock() + ", demandé : " + quantite + ")"
            );
        }

        produit.setQuantiteStock(produit.getQuantiteStock() - quantite);
        produitRepository.save(produit);

        MouvementStock mouvement = MouvementStock.builder()
                .type(TypeMouvement.SORTIE)
                .quantite(quantite)
                .dateMouvement(LocalDateTime.now())
                .produit(produit)
                .utilisateur(utilisateur)
                .build();

        mouvementStockRepository.save(mouvement);

        // Vérifie si on tombe sous le seuil d'alerte
        if (produit.getQuantiteStock() <= produit.getSeuilAlerte()) {
            declencherAlerte(produit);
        }
    }

    /**
     * Déclenche une alerte de stock bas.
     * Pour l'instant, on se contente d'un log — on pourra
     * enrichir ça plus tard (notification, email, etc.)
     */
    private void declencherAlerte(Produit produit) {
        System.out.println("⚠️ ALERTE STOCK BAS — Produit : " + produit.getNom()
                + " | Stock actuel : " + produit.getQuantiteStock()
                + " | Seuil : " + produit.getSeuilAlerte());
    }
}