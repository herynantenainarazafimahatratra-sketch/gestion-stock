package mg.eni.gestionstock.gestion_stock.controller;

import mg.eni.gestionstock.gestion_stock.entity.Produit;
import mg.eni.gestionstock.gestion_stock.service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitService produitService;

    @GetMapping
    public List<Produit> getAll() {
        return produitService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getById(@PathVariable Long id) {
        return ResponseEntity.ok(produitService.getById(id));
    }

    @GetMapping("/alertes")
    public List<Produit> getProduitsEnAlerte() {
        return produitService.getProduitsEnAlerte();
    }

    @PostMapping
    public ResponseEntity<Produit> create(@RequestBody Produit produit) {
        return ResponseEntity.ok(produitService.create(produit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> update(@PathVariable Long id, @RequestBody Produit produit) {
        return ResponseEntity.ok(produitService.update(id, produit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}