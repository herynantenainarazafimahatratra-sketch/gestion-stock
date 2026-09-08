package mg.eni.gestionstock.gestion_stock.repository;

import mg.eni.gestionstock.gestion_stock.entity.MouvementStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MouvementStockRepository extends JpaRepository<MouvementStock, Long> {
    List<MouvementStock> findByProduitId(Long produitId);
}