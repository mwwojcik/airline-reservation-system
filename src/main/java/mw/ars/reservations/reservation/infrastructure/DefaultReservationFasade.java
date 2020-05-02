package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationFasade;
import mw.ars.reservations.reservation.ReservationService;

public class DefaultReservationFasade implements ReservationFasade {

    private ReservationService service;

    public DefaultReservationFasade(ReservationService service) {
        this.service = service;
    }
}
