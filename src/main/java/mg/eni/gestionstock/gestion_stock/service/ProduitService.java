package mg.eni.gestionstock.gestion_stock.service;

import mg.eni.gestionstock.gestion_stock.entity.Produit;
import mg.eni.gestionstock.gestion_stock.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduitService {

    private final ProduitRepository produitRepository;

    public List<Produit> getAll() {
        return produitRepository.findAll();
    }

    public Produit getById(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable avec l'id : " + id));
    }

    public Produit create(Produit produit) {
        return produitRepository.save(produit);
    }

    public Produit update(Long id, Produit produitDetails) {
        Produit produit = getById(id);
        produit.setNom(produitDetails.getNom());
        produit.setReference(produitDetails.getReference());
        produit.setPrixAchat(produitDetails.getPrixAchat());
        produit.setPrixVente(produitDetails.getPrixVente());
        produit.setSeuilAlerte(produitDetails.getSeuilAlerte());
        produit.setCategorie(produitDetails.getCategorie());
        return produitRepository.save(produit);
    }

    public void delete(Long id) {
        Produit produit = getById(id);

        if (produit.getQuantiteStock() != 0) {
            throw new IllegalStateException(
                    "Impossible de supprimer ce produit : son stock n'est pas à 0 (actuellement " +
                            produit.getQuantiteStock() + "). Écoule ou ajuste le stock avant de le supprimer."
            );
        }

        produitRepository.deleteById(id);
    }

    // Produits dont le stock est bas
    public List<Produit> getProduitsEnAlerte() {
        return produitRepository.findAll().stream()
                .filter(p -> p.getQuantiteStock() <= p.getSeuilAlerte())
                .toList();
    }
}