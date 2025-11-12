package org.esprim.services;

import org.esprim.TpFoyer.entity.Chambre;
import org.esprim.TpFoyer.repositories.ChambreRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class ChambreServiceImpl implements IChambreService{
    ChambreRepository chambreRepository;

    public List<Chambre> retrieveAllChambres() {
        return chambreRepository.findAll();
    }

    public Chambre retrieveChambre(Long chambreId) {
        return chambreRepository.findById(chambreId).get();
    }

    public Chambre addChambre(Chambre c) {
        return chambreRepository.save(c);
    }

    public void removeChambre(Long chambreId) {
        chambreRepository.deleteById(chambreId);

    }

    public Chambre modifyChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }
}
