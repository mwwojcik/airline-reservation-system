package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationFacade;
import mw.ars.reservations.reservation.ReservationService;

public class DefaultReservationFacade implements ReservationFacade {

    private ReservationService service;

    public DefaultReservationFacade(ReservationService service) {
        this.service = service;
    }
}
