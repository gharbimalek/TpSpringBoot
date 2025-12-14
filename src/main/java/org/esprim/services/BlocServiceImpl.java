package org.esprim.services;

import org.esprim.TpFoyer.entity.Bloc;
import org.esprim.TpFoyer.entity.Chambre;
import org.esprim.TpFoyer.repositories.BlocRepository;
import org.esprim.TpFoyer.repositories.ChambreRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class BlocServiceImpl implements IBlocService {
    BlocRepository blocRepository;
    //nv
    ChambreRepository chambreRepository;

    public List<Bloc> retrieveAllBlocs() {
        return blocRepository.findAll();
    }

    public Bloc retrieveBloc(Long blocId) {
        return blocRepository.findById(blocId).orElse(null);
    }

    public Bloc addBloc(Bloc b) {
        return blocRepository.save(b);
    }

    public void removeBloc(Long blocId) {
        blocRepository.deleteById(blocId);
    }

    public Bloc modifyBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }
    //nv
    public Bloc affecterChambreABloc(List<Long> numChambres, Long idBloc){

        Bloc bloc = blocRepository.findById(idBloc).orElseThrow(()-> new RuntimeException("Bloc introuvable avec ID :" + idBloc));
        List<Chambre> chambres = chambreRepository.findAllByNumeroChambreIn(numChambres);

        if(chambres.size()!= numChambres.size()){
            throw new RuntimeException("une ou plz chambres sont introuvable");
        }
        for (Chambre chambre : chambres)
            if(chambre.getBloc()!=null && chambre.getBloc().getIdBloc()!=idBloc){
                throw new RuntimeException("la chambre" + chambre.getNumeroChambre() + "est deja affectee a un autre bloc");
            }
        for (Chambre chambre : chambres){
            chambre.setBloc(bloc);
        }
        if(bloc.getChambres()==null){
            bloc.setChambres(new HashSet<Chambre>());
        }
        bloc.getChambres().addAll(chambres);
        //sauvegarder les chamgements
        blocRepository.save(bloc);
        chambreRepository.saveAll(chambres);
        return bloc;

    }
}
