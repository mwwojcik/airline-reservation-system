package mw.ars.reservations.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mw.ars.commons.model.Result;
import mw.ars.reservations.reservation.common.model.ReservationId;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CancelledReservation implements IdentifiedReservation {
  @Getter private final ReservationId id;
  @Getter private final Status status;
  private final LocalDateTime cancellationDate;

  private CancelledReservation(ReservationId id) {
    this.id = id;
    status = Status.CANCELED;
    this.cancellationDate = LocalDateTime.now();
  }

  public static Result create(ConfirmedReservation confirmed) {
    return Result.successWithReturn(new CancelledReservation(confirmed.getId()));
  }

  public static Result create(HoldedReservation holded) {
    return Result.successWithReturn(new CancelledReservation(holded.getId()));
  }
}
