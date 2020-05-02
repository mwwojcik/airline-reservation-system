package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationRepository;
import mw.ars.reservations.reservation.ReservationService;

class DefaultReservationService implements ReservationService {
    private ReservationRepository repo;

    public DefaultReservationService(ReservationRepository repo) {
        this.repo = repo;
    }
}
