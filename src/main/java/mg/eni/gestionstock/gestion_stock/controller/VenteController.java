package mg.eni.gestionstock.gestion_stock.controller;

import mg.eni.gestionstock.gestion_stock.entity.Utilisateur;
import mg.eni.gestionstock.gestion_stock.entity.Vente;
import mg.eni.gestionstock.gestion_stock.service.VenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventes")
@RequiredArgsConstructor
public class VenteController {

    private final VenteService venteService;

    @GetMapping
    public List<Vente> getAll() {
        return venteService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vente> getById(@PathVariable Long id) {
        return ResponseEntity.ok(venteService.getById(id));
    }

    @GetMapping("/benefices")
    public List<VenteService.BeneficeMensuel> getBeneficeParMois() {
        return venteService.getBeneficeParMois();
    }

    @PostMapping
    public ResponseEntity<Vente> create(@RequestBody List<VenteService.LigneVenteRequest> lignes) {
        Utilisateur utilisateurTemp = Utilisateur.builder().id(1L).build();
        return ResponseEntity.ok(venteService.creerVente(lignes, utilisateurTemp));
    }
}
