package mg.eni.gestionstock.gestion_stock.repository;

import mg.eni.gestionstock.gestion_stock.entity.BonCommande;
import mg.eni.gestionstock.gestion_stock.enums.StatutCommande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BonCommandeRepository extends JpaRepository<BonCommande, Long> {
    List<BonCommande> findByStatut(StatutCommande statut);
}