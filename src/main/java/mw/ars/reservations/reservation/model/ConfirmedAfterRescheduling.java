package mw.ars.reservations.reservation.model;

import lombok.Getter;
import mw.ars.commons.model.*;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;
import java.util.UUID;

public class ConfirmedAfterRescheduling implements IdentifiedReservation {
  @Getter private final ReservationId id;
  @Getter private final CustomerId customerId;
  @Getter private final ReservationId originalReservationId;
  @Getter private final Status status;
  @Getter private final SeatNumber seat;
  @Getter private final Money price;
  @Getter private final LocalDateTime departureDate;
  @Getter private LocalDateTime created;
  @Getter private FlightId flightId;

  private ConfirmedAfterRescheduling(
      CustomerId customerId,
      ReservationId originalReservationId,
      FlightId flightId,
      SeatNumber seat,
      Money price,
      LocalDateTime departureDate) {
    this.customerId = customerId;
    this.flightId = flightId;
    this.id = ReservationId.of(UUID.randomUUID());
    this.originalReservationId = originalReservationId;
    this.seat = seat;
    this.price = price;
    this.departureDate = departureDate;
    this.status = Status.CONFIRMED;
  }

  public static Result create(
      CustomerId customerId,
      RescheduledReservation initialReservation,
      FlightId flightId,
      LocalDateTime newDepartureDate,
      Money newPrice,
      SeatNumber newSeatNumber) {
    return Result.successWithReturn(
        new ConfirmedAfterRescheduling(
            customerId,
            initialReservation.getId(),
            flightId,
            newSeatNumber,
            newPrice,
            newDepartureDate));
  }
}
