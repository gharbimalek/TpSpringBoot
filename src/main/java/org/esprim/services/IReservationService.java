package org.esprim.services;

import org.esprim.TpFoyer.entity.Reservation;

import java.sql.Date;
import java.util.List;

public interface IReservationService {
    public List<Reservation> retrieveAllReservations();
    public Reservation retrieveReservation(Long reservationId);
    public Reservation addReservation(Reservation r);
    public void removeReservation(Long reservationId);
    public Reservation modifyReservation(Reservation reservation);
    public Reservation ajouterReservation(long idBloc, long cinEtudiant);
    Reservation annulerReservation(long cinEtudiant);
    List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(Date anneeUniversite, String nomUniversite);



}
