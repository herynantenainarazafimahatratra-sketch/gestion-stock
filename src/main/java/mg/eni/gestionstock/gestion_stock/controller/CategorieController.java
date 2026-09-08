package mg.eni.gestionstock.gestion_stock.controller;

import mg.eni.gestionstock.gestion_stock.entity.Categorie;
import mg.eni.gestionstock.gestion_stock.service.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieService categorieService;

    @GetMapping
    public List<Categorie> getAll() {
        return categorieService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categorieService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Categorie> create(@RequestBody Categorie categorie) {
        return ResponseEntity.ok(categorieService.create(categorie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorie> update(@PathVariable Long id, @RequestBody Categorie categorie) {
        return ResponseEntity.ok(categorieService.update(id, categorie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categorieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}