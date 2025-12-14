package org.esprim.services;

import org.esprim.TpFoyer.entity.Chambre;
import org.esprim.TpFoyer.entity.Etudiant;
import org.esprim.TpFoyer.entity.Foyer;
import org.esprim.TpFoyer.entity.Reservation;
import org.esprim.TpFoyer.entity.Universite;
import org.esprim.TpFoyer.repositories.BlocRepository;
import org.esprim.TpFoyer.repositories.ChambreRepository;
import org.esprim.TpFoyer.repositories.EtudiantRepository;
import org.esprim.TpFoyer.repositories.ReservationRepository;
import org.esprim.TpFoyer.repositories.UniversiteRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import org.esprim.services.IReservationService;


import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements IReservationService {
    ReservationRepository reservationRepository;
    EtudiantRepository etudiantRepository;
    BlocRepository blocRepository;
    ChambreRepository chambreRepository;
    UniversiteRepository universiteRepository;


    public List<Reservation> retrieveAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation retrieveReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).orElse(null);
    }

    public Reservation addReservation(Reservation r) {
        return reservationRepository.save(r);
    }

    public void removeReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public Reservation modifyReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
    @Override
    public Reservation ajouterReservation(long idBloc, long cinEtudiant) {
        Etudiant etudiant = etudiantRepository.findAll()
                .stream()
                .filter(e -> e.getCin() != null && e.getCin().equals(cinEtudiant))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Etudiant introuvable avec CIN: " + cinEtudiant));

        Chambre chambreDisponible = chambreRepository.findByBloc_IdBlocAndTypeC(idBloc, null)
                .stream()
                .filter(ch -> {
                    int capaciteMax = switch (ch.getTypeC()) {
                        case SIMPLE -> 1;
                        case DOUBLE -> 2;
                        case TRIPLE -> 3;
                    };
                    return ch.getReservations().size() < capaciteMax;
                })
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Aucune chambre disponible dans ce bloc"));

        Reservation reservation = new Reservation();
        reservation.setAnneUniversitaire(new Date()); 
        reservation.setEstValide(true);

        reservation.setEtudiants(Collections.singleton(etudiant));
        if (etudiant.getReservations() == null) etudiant.setReservations(Collections.emptySet());
        etudiant.getReservations().add(reservation);

        if (chambreDisponible.getReservations() == null) chambreDisponible.setReservations(Collections.emptySet());
        chambreDisponible.getReservations().add(reservation);

        String numReservation = chambreDisponible.getNumeroChambre() + "-" +
                chambreDisponible.getBloc().getNomBloc() + "-" +
                (reservation.getAnneUniversitaire().getYear() + 1900); 

        reservationRepository.save(reservation);
        chambreRepository.save(chambreDisponible);
        etudiantRepository.save(etudiant);


        return reservation;
    }

    public Reservation annulerReservation(long cinEtudiant) {
        Etudiant etudiant = etudiantRepository.findAll()
                .stream()
                .filter(e -> e.getCin() != null && e.getCin().equals(cinEtudiant))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Etudiant introuvable avec CIN: " + cinEtudiant));
    
        Reservation reservation = etudiant.getReservations()
                .stream()
                .filter(Reservation::getEstValide)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Aucune réservation valide trouvée pour cet étudiant"));
    
        reservation.setEstValide(false);
    
        reservation.getEtudiants().remove(etudiant);
        etudiant.getReservations().remove(reservation);
    
        Chambre chambre = chambreRepository.findAll()
                .stream()
                .filter(ch -> ch.getReservations().contains(reservation))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("La réservation n'est associée à aucune chambre"));
    
        chambre.getReservations().remove(reservation);
    
        reservationRepository.save(reservation);
        etudiantRepository.save(etudiant);
        chambreRepository.save(chambre);
    
        return reservation;
    }

    public List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(Date anneeUniversite, String nomUniversite) {
    Universite universite = universiteRepository.findByNomUniversite(nomUniversite)
    .orElseThrow(() -> new RuntimeException("Université introuvable avec le nom : " + nomUniversite));


    Foyer foyer = universite.getFoyer();
    if (foyer == null) {
        throw new RuntimeException("Cette université n'a aucun foyer associé");
    }

    return foyer.getBlocs().stream()
            .flatMap(bloc -> bloc.getChambres().stream())
            .flatMap(chambre -> chambre.getReservations().stream())
            .filter(res -> {
                int anneeRes = res.getAnneUniversitaire().getYear();
                int anneeParam = anneeUniversite.getYear();
                return anneeRes == anneeParam;
            })
            .toList();
}

    @Override
    public List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(java.sql.Date anneeUniversite,
            String nomUniversite) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReservationParAnneeUniversitaireEtNomUniversite'");
    }


}
