package org.esprim.controllers;

import java.util.List;

import org.esprim.TpFoyer.entity.Chambre;
import org.esprim.services.IChambreService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
@RestController
@AllArgsConstructor
@RequestMapping("/chambre")
public class ChambreRestController {
    IChambreService chambreService;
    // http://localhost:8081/TpFoyer/chambre/retrieve-all-chambres
    @GetMapping("/retrieve-all-chambres")
    public List<Chambre> getChambres() {
    List<Chambre> listChambres = chambreService.retrieveAllChambres();
    return listChambres;
    }
    // http://localhost:8081/TpFoyer/chambre/retrieve-chambre/8
    @GetMapping("/retrieve-chambre/{chambre-id}")
    public Chambre retrieveChambre(@PathVariable("chambre-id") Long chId) {
    Chambre chambre = chambreService.retrieveChambre(chId);
    return chambre;
    }
    // http://localhost:8081/TpFoyer/chambre/add-chambre
    @PostMapping("/add-chambre")
    public Chambre addChambre(@RequestBody Chambre c) {
    Chambre chambre = chambreService.addChambre(c);
    return chambre;
    }
    // http://localhost:8081/TpFoyer/chambre/remove-chambre/{chambre-id}
    @DeleteMapping("/remove-chambre/{chambre-id}")
    public void removeChambre(@PathVariable("chambre-id") Long chId) {
    chambreService.removeChambre(chId);
    }
    // http://localhost:8081/TpFoyer/chambre/modify-chambre
    @PutMapping("/modify-chambre")
    public Chambre modifyChambre(@RequestBody Chambre c) {
    Chambre chambre = chambreService.modifyChambre(c);
    return chambre;
    }
}
