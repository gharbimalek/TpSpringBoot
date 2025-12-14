package org.esprim.services;

import org.esprim.TpFoyer.entity.Chambre;
import org.esprim.TpFoyer.entity.Foyer;
import org.esprim.TpFoyer.entity.Universite;
import org.esprim.TpFoyer.repositories.FoyerRepository;
import org.esprim.TpFoyer.repositories.UniversiteRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UniversiteServiceImpl implements IUniversiteService {
    UniversiteRepository universiteRepository;
    //nv
    FoyerRepository foyerRepository;

    public List<Universite> retrieveAllUniversites() {
        return universiteRepository.findAll();
    }

    public Universite retrieveUniversite(Long universiteId) {
        return universiteRepository.findById(universiteId).orElse(null);
    }

    public Universite addUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    public void removeUniversite(Long universiteId) {
        universiteRepository.deleteById(universiteId);
    }

    public Universite modifyUniversite(Universite universite) {
        return universiteRepository.save(universite);
    }

    //nv
    public Universite affecterFoyerAUniversite (Long idFoyer, String nomUniversite){
        Foyer foyer = foyerRepository.findById(idFoyer).orElseThrow(() -> new RuntimeException("Foyer introuvable avec l'id:" + idFoyer));
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite).orElseThrow(() -> new RuntimeException("Universite introuvable avec le nom:" +" " + nomUniversite));
        if(foyer.getUniversite()!= null || universite.getFoyer() != null){
            throw new RuntimeException("L'association existe deja pour ce foyer" + "ou cette universite.");
        }
        universite.setFoyer(foyer);
        foyer.setUniversite(universite);
        universiteRepository.save(universite);
        foyerRepository.save(foyer);
        return universite;

    }
    //nv
    public Universite desaffecterFoyerAUniversite(Long idUniversite) {
    Universite universite = universiteRepository.findById(idUniversite).orElseThrow(() -> new RuntimeException("Université introuvable avec l'id : " + idUniversite));
    Foyer foyer = universite.getFoyer();
    if (universite.getFoyer() == null) {
        throw new RuntimeException("Cette universite n'a aucun foyer associe.");
    }
    universite.setFoyer(null);
    foyer.setUniversite(null);
    universiteRepository.save(universite);
    foyerRepository.save(foyer);
    return universite;
    }

 
}
