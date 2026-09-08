package mg.eni.gestionstock.gestion_stock.controller;

import mg.eni.gestionstock.gestion_stock.entity.Fournisseur;
import mg.eni.gestionstock.gestion_stock.service.FournisseurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
@RequiredArgsConstructor
public class FournisseurController {

    private final FournisseurService fournisseurService;

    @GetMapping
    public List<Fournisseur> getAll() {
        return fournisseurService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> getById(@PathVariable Long id) {
        return ResponseEntity.ok(fournisseurService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Fournisseur> create(@RequestBody Fournisseur fournisseur) {
        return ResponseEntity.ok(fournisseurService.create(fournisseur));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> update(@PathVariable Long id, @RequestBody Fournisseur fournisseur) {
        return ResponseEntity.ok(fournisseurService.update(id, fournisseur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fournisseurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}