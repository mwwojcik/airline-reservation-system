package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationFacade;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ReservationController {
    private ReservationFacade fasade;

    ReservationController(ReservationFacade fasade) {
        this.fasade = fasade;
    }
}
