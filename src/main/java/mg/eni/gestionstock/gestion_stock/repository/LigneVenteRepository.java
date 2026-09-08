package mg.eni.gestionstock.gestion_stock.repository;

import mg.eni.gestionstock.gestion_stock.entity.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneVenteRepository extends JpaRepository<LigneVente, Long> {
}