package mw.ars.reservations.reservation.domain;

import lombok.Value;
import mw.ars.commons.model.CustomerId;
import mw.ars.commons.model.FlightId;
import mw.ars.commons.model.SeatNumber;
import mw.ars.reservations.reservation.model.ConfirmedAfterRescheduling;
import mw.ars.reservations.reservation.model.ConfirmedReservation;
import mw.ars.reservations.reservation.model.IdentifiedReservation;
import mw.ars.reservations.reservation.model.RescheduledReservation;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;

public class RescheduleReservationDomainService {

  public static RescheduledResultWrapper reschedule(
          CustomerId customerId,
      IdentifiedReservation original,
      int rescheduledSoFar,
      FlightId newFlightId,
      LocalDateTime newDepartureTime,
      SeatNumber newSeatNumber,
      Money newInitialPrice) {

    if (original instanceof ConfirmedReservation) {
      var confirmedOriginal = toConfirmed(original);
      var result = confirmedOriginal.reschedule(rescheduledSoFar);

      if (result.isFailure()) {
        throw new IllegalStateException("Reservation cannot be rescheduled!");
      }
      RescheduledReservation res=(RescheduledReservation) result.returned();
      var newPrice= calculate(newInitialPrice);
      var confirmedResult=ConfirmedAfterRescheduling.create(customerId,res,newFlightId,newDepartureTime,newInitialPrice,newSeatNumber);

      if (confirmedResult.isFailure()) {
        throw new IllegalStateException("Reservation cannot be rescheduled!");
      }
      return new RescheduledResultWrapper(res, ((ConfirmedAfterRescheduling)confirmedResult.returned()));

    } else {
      throw new IllegalStateException("Reservation should be CONFIRMED!");
    }
  }

  private static ConfirmedReservation toConfirmed(IdentifiedReservation original) {
    return (ConfirmedReservation) original;
  }

  @Value
  public static class RescheduledResultWrapper{
    private RescheduledReservation rescheduled;
    private ConfirmedAfterRescheduling confirmed;
  }

  private static Money calculate(Money initialPrice) {
    // todo price policy
    return Money.of(200, "USD");
  }
}
