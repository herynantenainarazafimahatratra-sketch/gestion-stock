package mg.eni.gestionstock.gestion_stock.controller;

import mg.eni.gestionstock.gestion_stock.entity.MouvementStock;
import mg.eni.gestionstock.gestion_stock.entity.Utilisateur;
import mg.eni.gestionstock.gestion_stock.repository.MouvementStockRepository;
import mg.eni.gestionstock.gestion_stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;
    private final MouvementStockRepository mouvementStockRepository;

    public record MouvementRequest(Long produitId, Integer quantite) {}

    @GetMapping("/mouvements")
    public List<MouvementStock> getMouvements() {
        return mouvementStockRepository.findAll();
    }

    @PostMapping("/entree")
    public ResponseEntity<Void> entreeStock(@RequestBody MouvementRequest request) {
        Utilisateur utilisateurTemp = Utilisateur.builder().id(1L).build();
        stockService.entreeStock(request.produitId(), request.quantite(), utilisateurTemp);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sortie")
    public ResponseEntity<Void> sortieStock(@RequestBody MouvementRequest request) {
        Utilisateur utilisateurTemp = Utilisateur.builder().id(1L).build();
        stockService.sortieStock(request.produitId(), request.quantite(), utilisateurTemp);
        return ResponseEntity.ok().build();
    }
}