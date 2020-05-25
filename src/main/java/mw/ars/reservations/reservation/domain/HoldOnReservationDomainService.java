package mw.ars.reservations.reservation.domain;

import mw.ars.reservations.reservation.model.HoldedReservation;
import mw.ars.reservations.reservation.model.IdentifiedReservation;
import mw.ars.reservations.reservation.model.RegisteredReservation;

import java.util.Optional;

public class HoldOnReservationDomainService {
  public static HoldedReservation hold(IdentifiedReservation reservation, int currentlyHolded) {
    var registered =
        toRegistered(reservation)
            .orElseThrow(() -> new IllegalStateException("Reservation should be REGISTERED!"));
    var res = registered.hold(currentlyHolded);
    if (res.isFailure()) {
      throw new IllegalArgumentException("Reservation not holded!");
    }
    return ((HoldedReservation) res.returned());
  }

  private static Optional<RegisteredReservation> toRegistered(IdentifiedReservation reservation) {
    return reservation instanceof RegisteredReservation
        ? Optional.of((RegisteredReservation) reservation)
        : Optional.empty();
  }
}
