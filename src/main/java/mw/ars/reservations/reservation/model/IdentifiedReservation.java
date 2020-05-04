package mw.ars.reservations.reservation.model;

import mw.ars.reservations.reservation.common.model.ReservationId;

interface IdentifiedReservation {
    Status getStatus();
    ReservationId getId();
}
