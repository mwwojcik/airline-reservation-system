package mw.ars.reservations.reservation.model;

import lombok.Getter;
import mw.ars.commons.model.Result;
import mw.ars.reservations.reservation.common.model.ReservationId;
import mw.ars.reservations.reservation.common.model.SeatNumber;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;

class RegisteredReservation implements IdentifiedReservation {
  @Getter private ReservationId id;
  @Getter private Status status;
  private SeatNumber seat;
  private Money price;
  private LocalDateTime departureDate;

  public RegisteredReservation(
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

  public Result hold(int currentlyHolded) {
    return HoldedReservation.create(this,this.departureDate,currentlyHolded);
  }

  public Result confirm() {
    return ConfirmedReservation.create(this);
  }
}
