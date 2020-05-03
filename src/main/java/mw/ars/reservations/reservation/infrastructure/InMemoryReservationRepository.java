package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationRepository;
import mw.ars.reservations.reservation.common.model.ReservationId;
import mw.ars.reservations.reservation.model.Reservation;

import java.util.HashMap;
import java.util.Map;

public class InMemoryReservationRepository implements ReservationRepository {
    Map<ReservationId, Reservation> cache=new HashMap<>();
}
