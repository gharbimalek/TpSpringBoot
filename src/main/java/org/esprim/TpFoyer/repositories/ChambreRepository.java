package org.esprim.TpFoyer.repositories;

import org.esprim.TpFoyer.entity.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ChambreRepository extends JpaRepository<Chambre,Long> {

}
