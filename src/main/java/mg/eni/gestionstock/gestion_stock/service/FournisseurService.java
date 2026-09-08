package mg.eni.gestionstock.gestion_stock.service;

import mg.eni.gestionstock.gestion_stock.entity.Fournisseur;
import mg.eni.gestionstock.gestion_stock.repository.FournisseurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FournisseurService {

    private final FournisseurRepository fournisseurRepository;

    public List<Fournisseur> getAll() {
        return fournisseurRepository.findAll();
    }

    public Fournisseur getById(Long id) {
        return fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur introuvable avec l'id : " + id));
    }

    public Fournisseur create(Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }

    public Fournisseur update(Long id, Fournisseur details) {
        Fournisseur fournisseur = getById(id);
        fournisseur.setNom(details.getNom());
        fournisseur.setContact(details.getContact());
        fournisseur.setAdresse(details.getAdresse());
        return fournisseurRepository.save(fournisseur);
    }

    public void delete(Long id) {
        fournisseurRepository.deleteById(id);
    }
}