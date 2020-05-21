package mw.ars.reservations.reservation.model;

import mw.ars.reservations.reservation.common.model.ReservationId;

public interface IdentifiedReservation {
  Status getStatus();

  ReservationId getId();
}
