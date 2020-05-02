package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationRepository;
import mw.ars.reservations.reservation.infrastructure.db.ReservationRepositoryDB;

class DefaultReservationRepository implements ReservationRepository {

    private ReservationRepositoryDB repositoryDB;

    public DefaultReservationRepository(ReservationRepositoryDB repositoryDB) {
        this.repositoryDB = repositoryDB;
    }
}
