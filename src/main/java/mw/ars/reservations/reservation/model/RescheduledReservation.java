package mw.ars.reservations.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mw.ars.commons.model.ReservationId;
import mw.ars.commons.model.Result;

@AllArgsConstructor
@Getter
public class RescheduledReservation implements IdentifiedReservation {
  private static final int TWO_WEEKS_DAYS = 14;
  @Getter private final ReservationId id;
  @Getter private final Status status;

  private RescheduledReservation(ReservationId id) {
    this.id = id;
    this.status = Status.RESCHEDULED;
  }

  public static Result create(ConfirmedReservation confirmed, int rescheduled) {

    var rescheduledSoFar = RescheduledSoFar.of(rescheduled);

    if (rescheduledSoFar.limitReached()) {
      return Result.failure();
    }
    return Result.successWithReturn(new RescheduledReservation(confirmed.getId()));
  }

  public static RescheduledReservation of(ReservationId reservationId) {
    return new RescheduledReservation(reservationId);
  }

  @AllArgsConstructor
  public static class RescheduledSoFar {

    static int RESCHEDULING_LIMIT = 3;
    int rescheduledSoFar;

    public static RescheduledSoFar of(int rescheduledSoFar) {
      return new RescheduledSoFar(rescheduledSoFar);
    }

    boolean limitReached() {
      return rescheduledSoFar == RESCHEDULING_LIMIT;
    }
  }
}
