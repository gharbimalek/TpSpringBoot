package org.esprim.TpFoyer.repositories;

import java.util.Optional;

import org.esprim.TpFoyer.entity.Universite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UniversiteRepository extends JpaRepository<Universite,Long>{
    //nv
    Optional<Universite>findByNomUniversite(String nom);

}
