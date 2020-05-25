package mw.ars.reservations.reservation.domain;

import mw.ars.commons.model.Result;
import mw.ars.reservations.reservation.model.ConfirmedReservation;
import mw.ars.reservations.reservation.model.HoldedReservation;
import mw.ars.reservations.reservation.model.IdentifiedReservation;
import mw.ars.reservations.reservation.model.RegisteredReservation;

public class ConfirmReservationDomainService {
  public static ConfirmedReservation confirm(IdentifiedReservation reservation) {
    Result res = null;

    if (reservation instanceof HoldedReservation) {
      var holded = toHolded(reservation);
      res = holded.confirm();
    } else if (reservation instanceof RegisteredReservation) {
      var registered = toRegistered(reservation);
      res = registered.confirm();
    } else {
      throw new IllegalStateException("Reservation should be REGISTERED or HOLDED!");
    }

    if (res.isFailure()) {
      throw new IllegalArgumentException("Reservation not confirmed!");
    }
    return ((ConfirmedReservation) res.returned());
  }

  private static RegisteredReservation toRegistered(IdentifiedReservation reservation) {
    return (RegisteredReservation) reservation;
  }

  private static HoldedReservation toHolded(IdentifiedReservation reservation) {
    return (HoldedReservation) reservation;
  }
}
