package org.esprim.TpFoyer.repositories;

import org.esprim.TpFoyer.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

}
