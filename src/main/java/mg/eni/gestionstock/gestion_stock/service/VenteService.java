package mg.eni.gestionstock.gestion_stock.service;

import mg.eni.gestionstock.gestion_stock.entity.*;
import mg.eni.gestionstock.gestion_stock.repository.ProduitRepository;
import mg.eni.gestionstock.gestion_stock.repository.VenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class VenteService {

    private final VenteRepository venteRepository;
    private final ProduitRepository produitRepository;
    private final StockService stockService;

    /**
     * Crée une vente à partir d'une liste de lignes (produit + quantité),
     * calcule le montant total, et décrémente le stock pour chaque produit.
     */
    @Transactional
    public Vente creerVente(List<LigneVenteRequest> lignesRequest, Utilisateur utilisateur) {
        if (lignesRequest == null || lignesRequest.isEmpty()) {
            throw new IllegalArgumentException("Une vente doit contenir au moins une ligne");
        }

        Vente vente = Vente.builder()
                .dateVente(LocalDateTime.now())
                .montantTotal(BigDecimal.ZERO)
                .utilisateur(utilisateur)
                .lignes(new java.util.ArrayList<>())
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (LigneVenteRequest ligneReq : lignesRequest) {
            Produit produit = produitRepository.findById(ligneReq.produitId())
                    .orElseThrow(() -> new RuntimeException("Produit introuvable avec l'id : " + ligneReq.produitId()));

            // Décrémente le stock (lève une exception si stock insuffisant)
            stockService.sortieStock(produit.getId(), ligneReq.quantite(), utilisateur);

            LigneVente ligne = LigneVente.builder()
                    .quantite(ligneReq.quantite())
                    .prixUnitaire(produit.getPrixVente())
                    .produit(produit)
                    .vente(vente)
                    .build();

            vente.getLignes().add(ligne);

            BigDecimal sousTotal = produit.getPrixVente().multiply(BigDecimal.valueOf(ligneReq.quantite()));
            total = total.add(sousTotal);
        }

        vente.setMontantTotal(total);

        return venteRepository.save(vente);
    }

    public List<Vente> getAll() {
        return venteRepository.findAll();
    }

    public Vente getById(Long id) {
        return venteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vente introuvable avec l'id : " + id));
    }

    /**
     * Calcule le chiffre d'affaires, le coût et le bénéfice, groupés par mois,
     * sur la base de toutes les ventes enregistrées.
     *
     * Bénéfice d'une ligne = (prixUnitaire de la ligne - prixAchat actuel du produit) * quantité.
     * Note : prixAchat n'étant pas historisé, un changement de prix d'achat
     * impacte rétroactivement le bénéfice recalculé sur les anciennes ventes.
     */
    @Transactional(readOnly = true)
    public List<BeneficeMensuel> getBeneficeParMois() {
        List<Vente> ventes = venteRepository.findAll();

        Map<YearMonth, BigDecimal> chiffreAffairesParMois = new TreeMap<>();
        Map<YearMonth, BigDecimal> coutParMois = new TreeMap<>();

        for (Vente vente : ventes) {
            YearMonth mois = YearMonth.from(vente.getDateVente());

            for (LigneVente ligne : vente.getLignes()) {
                BigDecimal quantite = BigDecimal.valueOf(ligne.getQuantite());
                BigDecimal ca = ligne.getPrixUnitaire().multiply(quantite);
                BigDecimal cout = ligne.getProduit().getPrixAchat().multiply(quantite);

                chiffreAffairesParMois.merge(mois, ca, BigDecimal::add);
                coutParMois.merge(mois, cout, BigDecimal::add);
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        return chiffreAffairesParMois.entrySet().stream()
                .map(entry -> {
                    YearMonth mois = entry.getKey();
                    BigDecimal ca = entry.getValue();
                    BigDecimal cout = coutParMois.getOrDefault(mois, BigDecimal.ZERO);
                    BigDecimal benefice = ca.subtract(cout);
                    return new BeneficeMensuel(mois.format(formatter), ca, cout, benefice);
                })
                .toList();
    }

    /**
     * DTO interne représentant une ligne de vente demandée par le client
     * (produit + quantité), avant calcul du prix et création en base.
     */
    public record LigneVenteRequest(Long produitId, Integer quantite) {}

    /**
     * DTO de sortie pour le bénéfice mensuel.
     */
    public record BeneficeMensuel(
            String mois,
            BigDecimal chiffreAffaires,
            BigDecimal cout,
            BigDecimal benefice
    ) {}
}
