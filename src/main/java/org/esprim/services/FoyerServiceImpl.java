package org.esprim.services;

import org.esprim.TpFoyer.entity.Foyer;
import org.esprim.TpFoyer.repositories.FoyerRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import java.util.List;

@Service
@AllArgsConstructor
public class FoyerServiceImpl implements IFoyerService {
    FoyerRepository foyerRepository;

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
}
