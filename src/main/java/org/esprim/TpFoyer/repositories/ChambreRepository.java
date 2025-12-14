package org.esprim.TpFoyer.repositories;

import java.util.List;
import java.util.Set;

import org.esprim.TpFoyer.entity.Chambre;
import org.esprim.TpFoyer.entity.TypeChambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface ChambreRepository extends JpaRepository<Chambre,Long> {
    List<Chambre> findAllByNumeroChambreIn(List<Long> numeroChambre);

    @Query("SELECT c " + "FROM Chambre c " + "WHERE c.bloc.foyer.universite.nomUniversite" + "= :nom")
    Set<Chambre> trouverChambresParNomUniversite(@Param("nom") String nomUniversite );

    Set<Chambre> findByBloc_IdBlocAndTypeC(Long idBloc, TypeChambre typeC);

    @Query("SELECT c FROM Chambre c WHERE c.bloc.idBloc = ?1 AND c.typeC = ?2")
    Set<Chambre> getChambresJPQL(@Param("idBloc") Long idBloc, @Param("typeC") TypeChambre typeC);


}
