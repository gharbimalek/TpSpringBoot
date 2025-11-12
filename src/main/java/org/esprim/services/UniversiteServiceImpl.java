package org.esprim.services;

import org.esprim.TpFoyer.entity.Universite;
import org.esprim.TpFoyer.repositories.UniversiteRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import java.util.List;

@Service
@AllArgsConstructor
public class UniversiteServiceImpl implements IUniversiteService {
    UniversiteRepository universiteRepository;

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
}
