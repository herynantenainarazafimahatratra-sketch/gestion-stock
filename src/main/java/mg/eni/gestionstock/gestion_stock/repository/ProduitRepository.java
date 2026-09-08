package mg.eni.gestionstock.gestion_stock.repository;

import mg.eni.gestionstock.gestion_stock.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Optional<Produit> findByReference(String reference);

    // Produits dont le stock est sous le seuil d'alerte
    List<Produit> findByQuantiteStockLessThanEqual(Integer seuil);
}