package org.esprim.services;

import java.util.List;
import java.util.Set;

import org.esprim.TpFoyer.entity.Chambre;
import org.esprim.TpFoyer.entity.TypeChambre;

public interface IChambreService {
    public List<Chambre> retrieveAllChambres();
    public Chambre retrieveChambre(Long chambreId);
    public Chambre addChambre(Chambre c);
    public void removeChambre(Long chambreId);
    public Chambre modifyChambre(Chambre chambre);

    Set<Chambre> getChambresParNomUniversite(String nomUniversite);
    Set<Chambre> getChambresParBlocEtType(long idBloc, TypeChambre typeC);
    List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(String nomUniversite, TypeChambre type);



}
