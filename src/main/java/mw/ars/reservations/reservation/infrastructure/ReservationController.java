package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationFasade;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ReservationController {
    private ReservationFasade fasade;

    ReservationController(ReservationFasade fasade) {
        this.fasade = fasade;
    }
}
