package mg.eni.gestionstock.gestion_stock.repository;

import mg.eni.gestionstock.gestion_stock.entity.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenteRepository extends JpaRepository<Vente, Long> {
}