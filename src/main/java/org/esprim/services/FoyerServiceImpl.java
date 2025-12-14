package org.esprim.services;

import org.esprim.TpFoyer.entity.Bloc;
import org.esprim.TpFoyer.entity.Foyer;
import org.esprim.TpFoyer.entity.Universite;
import org.esprim.TpFoyer.repositories.FoyerRepository;
import org.esprim.TpFoyer.repositories.UniversiteRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class FoyerServiceImpl implements IFoyerService {

    FoyerRepository foyerRepository;
//nv
    UniversiteRepository universiteRepository;


    public List<Foyer> retrieveAllFoyers() {
        return foyerRepository.findAll();
    }

    public Foyer retrieveFoyer(Long foyerId) {
        return foyerRepository.findById(foyerId).orElse(null);
    }

    public Foyer addFoyer(Foyer f) {
        return foyerRepository.save(f);
    }

    public void removeFoyer(Long foyerId) {
        foyerRepository.deleteById(foyerId);
    }

    public Foyer modifyFoyer(Foyer foyer) {
        return foyerRepository.save(foyer);
    }


//nv
    
    public Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, Long idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new RuntimeException("Université introuvable "));
    
        if (universite.getFoyer() != null) {
            throw new RuntimeException("Cette université a déjà un foyer associé !");
        }
    
        foyer.setUniversite(universite);
    
        Set<Bloc> blocs = foyer.getBlocs();
            for (Bloc bloc : blocs) {
                bloc.setFoyer(foyer);
            }


        Foyer savedFoyer = foyerRepository.save(foyer);
        universite.setFoyer(savedFoyer);
        universiteRepository.save(universite);
    
        return savedFoyer;
    }


 
}


