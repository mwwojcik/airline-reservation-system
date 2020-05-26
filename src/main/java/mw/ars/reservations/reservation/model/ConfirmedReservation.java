package mw.ars.reservations.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mw.ars.commons.model.FlightId;
import mw.ars.commons.model.ReservationId;
import mw.ars.commons.model.Result;
import mw.ars.commons.model.SeatNumber;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ConfirmedReservation implements IdentifiedReservation {
  @Getter private final ReservationId id;
  @Getter private Status status;

  private ConfirmedReservation(ReservationId id) {
    this.id = id;
    status = Status.CONFIRMED;
  }

  public static Result create(RegisteredReservation registered) {
    return Result.successWithReturn(new ConfirmedReservation(registered.getId()));
  }

  public static Result create(HoldedReservation registered) {
    return Result.successWithReturn(new ConfirmedReservation(registered.getId()));
  }

  public static ConfirmedReservation of(ReservationId reservationId) {
    return new ConfirmedReservation(reservationId);
  }

 public Result reschedule(int rescheduledSoFar) {
    return RescheduledReservation.create(this,rescheduledSoFar);
  }

  public Result cancel() {
    return CancelledReservation.create(this);
  }
}
