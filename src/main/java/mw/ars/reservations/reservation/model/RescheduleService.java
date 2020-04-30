package mw.ars.reservations.reservation.model;

import mw.ars.reservations.reservation.common.model.FligtId;
import mw.ars.reservations.reservation.common.model.SeatNumber;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;

class RescheduleService {

  Reservation reschedule(
      Reservation original,
      FligtId newFlightId,
      SeatNumber newSeatNumber,
      LocalDateTime newDepartureDate,
      Money newPrice) {

    var result = original.reschedule();

    if (result.isFailure()) {
      // throw exception
    }

    var rescheduled = (Reservation.ReservationBuilder) result.returned();

    return rescheduled
        .departureDate(newDepartureDate)
        .flightId(newFlightId)
        .price(newPrice)
        .seat(newSeatNumber)
        .build();
  }
}
