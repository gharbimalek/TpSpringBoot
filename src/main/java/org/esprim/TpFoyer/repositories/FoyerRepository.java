package org.esprim.TpFoyer.repositories;

import org.esprim.TpFoyer.entity.Foyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FoyerRepository extends JpaRepository<Foyer,Long> {

}
