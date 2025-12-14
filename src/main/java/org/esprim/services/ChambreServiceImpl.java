package org.esprim.services;

import org.esprim.TpFoyer.entity.Chambre;
import org.esprim.TpFoyer.entity.TypeChambre;
import org.esprim.TpFoyer.repositories.BlocRepository;
import org.esprim.TpFoyer.repositories.ChambreRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.LocalDate;
import java.time.ZoneId;



@Slf4j
@Service
@AllArgsConstructor
public class ChambreServiceImpl implements IChambreService{
    ChambreRepository chambreRepository;
    BlocRepository blocRepository;
    

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

    public Set<Chambre>getChambresParNomUniversite(String nomUniversite){
        return chambreRepository.trouverChambresParNomUniversite(nomUniversite);
    }


    public Set<Chambre> getChambresParBlocEtType(long idBloc, TypeChambre typeC) {

            blocRepository.findById(idBloc).orElseThrow(() -> new RuntimeException("Bloc introuvable avec id : " + idBloc));

            return chambreRepository.findByBloc_IdBlocAndTypeC(idBloc, typeC);
    }

    @Scheduled(cron = "0/15 * * * * *")
    public void pourcentageChambreParTypeChambre(){
        List<Chambre> chambres = chambreRepository.findAll();
        int totalChambres = chambres.size();
        log.info("Nbre total des chambres:" +totalChambres);

        if(totalChambres>0){
            Map<String, Integer> countByType = new HashMap<>();
            for (Chambre chambre : chambres){
                String type = String.valueOf(chambre.getTypeC());
                countByType.put(type,countByType.getOrDefault(type, 0)+1);
                
            }
            for(Map.Entry<String, Integer>entry:countByType.entrySet()){
                String type = entry.getKey();
                int count = entry.getValue();
            }
        }

    }

    public List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(String nomUniversite, TypeChambre type) {
        Set<Chambre> chambres = chambreRepository.trouverChambresParNomUniversite(nomUniversite);
    
        int anneeCourante = LocalDate.now().getYear();
    
        return chambres.stream()
                .filter(ch -> ch.getTypeC() == type)
                .filter(ch -> {
                    boolean reserveCetteAnnee = ch.getReservations().stream()
                            .anyMatch(res -> res.getEstValide() &&
                                    (res.getAnneUniversitaire().toInstant()
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDate()
                                            .getYear() == anneeCourante));
                    return !reserveCetteAnnee;
                })
                .toList();
    }



}
