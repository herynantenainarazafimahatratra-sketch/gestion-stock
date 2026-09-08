package mg.eni.gestionstock.gestion_stock.repository;

import mg.eni.gestionstock.gestion_stock.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}