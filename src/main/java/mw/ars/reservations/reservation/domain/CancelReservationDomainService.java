package mw.ars.reservations.reservation.domain;

import mw.ars.commons.model.Result;
import mw.ars.reservations.reservation.model.*;

public class CancelReservationDomainService {
    public static CancelledReservation cancel(IdentifiedReservation reservation) {
        Result res = null;

        if (reservation instanceof HoldedReservation) {
            var holded = toHolded(reservation);
            res = holded.cancel();
        } else if (reservation instanceof ConfirmedReservation) {
            var confirmed = toConfirmed(reservation);
            res = confirmed.cancel();
        } else {
            throw new IllegalStateException("Reservation should be HOLDED or CONFIRMED!");
        }

        if (res.isFailure()) {
            throw new IllegalArgumentException("Reservation not cancelled!");
        }
        return ((CancelledReservation) res.returned());
    }

    private static ConfirmedReservation toConfirmed(IdentifiedReservation reservation) {
        return (ConfirmedReservation) reservation;
    }

    private static HoldedReservation toHolded(IdentifiedReservation reservation) {
        return (HoldedReservation) reservation;
    }
}
