package mw.ars.reservations.reservation.domain;

import mw.ars.commons.model.Result;
import mw.ars.reservations.reservation.model.ConfirmedReservation;
import mw.ars.reservations.reservation.model.HoldedReservation;
import mw.ars.reservations.reservation.model.IdentifiedReservation;
import mw.ars.reservations.reservation.model.RegisteredReservation;

import java.util.Optional;

public class ConfirmReservationDomainService {
  public static ConfirmedReservation confirm(IdentifiedReservation reservation) {
    Result res=null;

    if (reservation instanceof HoldedReservation) {
      var holded =
              toHolded(reservation)
                      .orElseThrow(() -> new IllegalStateException("Reservation should be REGISTERED or HOLDED!"));
      res = holded.confirm();
    } else if (reservation instanceof RegisteredReservation) {
      var registered =
              toRegistered(reservation)
                      .orElseThrow(() -> new IllegalStateException("Reservation should be REGISTERED or HOLDED!"));
      res = registered.confirm();
    }

    if (res.isFailure()) {
      throw new IllegalArgumentException("Reservation not confirmed!");
    }
    return ((ConfirmedReservation) res.returned());
  }

  private static Optional<RegisteredReservation> toRegistered(IdentifiedReservation reservation) {
    return reservation instanceof RegisteredReservation
        ? Optional.of((RegisteredReservation) reservation)
        : Optional.empty();
  }

  private static Optional<HoldedReservation> toHolded(IdentifiedReservation reservation) {
    return reservation instanceof HoldedReservation
        ? Optional.of((HoldedReservation) reservation)
        : Optional.empty();
  }
}
