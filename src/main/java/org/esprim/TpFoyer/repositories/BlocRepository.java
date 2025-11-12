package org.esprim.TpFoyer.repositories;

import org.esprim.TpFoyer.entity.Bloc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BlocRepository extends JpaRepository<Bloc,Long>{

}
