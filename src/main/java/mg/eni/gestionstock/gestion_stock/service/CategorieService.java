package mg.eni.gestionstock.gestion_stock.service;

import mg.eni.gestionstock.gestion_stock.entity.Categorie;
import mg.eni.gestionstock.gestion_stock.repository.CategorieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategorieService {

    private final CategorieRepository categorieRepository;

    public List<Categorie> getAll() {
        return categorieRepository.findAll();
    }

    public Categorie getById(Long id) {
        return categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable avec l'id : " + id));
    }

    public Categorie create(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public Categorie update(Long id, Categorie categorieDetails) {
        Categorie categorie = getById(id);
        categorie.setNom(categorieDetails.getNom());
        return categorieRepository.save(categorie);
    }

    public void delete(Long id) {
        categorieRepository.deleteById(id);
    }
}