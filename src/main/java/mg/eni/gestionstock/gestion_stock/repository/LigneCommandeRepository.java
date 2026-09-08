package mg.eni.gestionstock.gestion_stock.repository;

import mg.eni.gestionstock.gestion_stock.entity.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {
}