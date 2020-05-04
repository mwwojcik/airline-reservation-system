package mw.ars.reservations.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mw.ars.commons.model.Result;
import mw.ars.reservations.reservation.common.model.CustomerId;
import mw.ars.reservations.reservation.common.model.FligtId;
import mw.ars.reservations.reservation.common.model.ReservationId;
import mw.ars.reservations.reservation.common.model.SeatNumber;
import org.javamoney.moneta.Money;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class ConfirmedReservation {
  @Getter private ReservationId id;
  @Getter private Status status;

  private ConfirmedReservation(ReservationId id) {
    this.id=id;
    status=Status.CONFIRMED;
  }

  public static Result create(RegisteredReservation registered) {
    return Result.successWithReturn(new ConfirmedReservation(registered.getId()));
  }

  public Result reschedule(
          ConfirmedReservation confirmed,
          int rescheduled,
          FligtId newFlightId,
          SeatNumber newSeatNumber,
          LocalDateTime newDepartureDate,
          Money newPrice) {

    return RescheduledReservation.create(this,rescheduled,newFlightId,newSeatNumber,newDepartureDate,newPrice);
  }

  public Result cancel() {
   return CancelledReservation.create(this) ;
   }

}
