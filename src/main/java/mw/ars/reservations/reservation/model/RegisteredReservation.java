package mw.ars.reservations.reservation.model;

import lombok.Getter;
import mw.ars.commons.model.FlightId;
import mw.ars.commons.model.ReservationId;
import mw.ars.commons.model.Result;
import mw.ars.commons.model.SeatNumber;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;

public class RegisteredReservation implements IdentifiedReservation {
  @Getter private final ReservationId id;
  @Getter private final Status status;
  @Getter private final SeatNumber seat;
  @Getter private final Money price;
  @Getter private final LocalDateTime departureDate;

  private RegisteredReservation(
      ReservationId initialResId, SeatNumber seat, Money price, LocalDateTime departureDate) {
    this.id = ReservationId.of(initialResId.getId());
    this.seat = seat;
    this.price = price;
    this.departureDate = departureDate;
    this.status = Status.REGISTERED;
  }

  public static Result create(
      InitialReservation initialReservation,
      SeatNumber seat,
      Money price,
      LocalDateTime departureDate) {
    return Result.successWithReturn(
        new RegisteredReservation(initialReservation.getId(), seat, price, departureDate));
  }

  public static RegisteredReservation of(
      ReservationId reservationId, SeatNumber seat, Money price, LocalDateTime departureDate) {
    return new RegisteredReservation(reservationId, seat, price, departureDate);
  }

  public Result hold(int currentlyHolded) {
    return HoldedReservation.create(this, this.departureDate, currentlyHolded);
  }

  public Result confirm() {
    return ConfirmedReservation.create(this);
  }

  @Override
  public FlightId getFlightId() {
    return null;
  }
}
