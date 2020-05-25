package mw.ars.reservations.reservation.model;

import mw.ars.commons.model.FlightId;
import mw.ars.commons.model.ReservationId;

public interface IdentifiedReservation {
  Status getStatus();

  ReservationId getId();
}
