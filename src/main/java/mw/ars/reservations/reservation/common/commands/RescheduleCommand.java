package mw.ars.reservations.reservation.common.commands;

import lombok.Value;
import mw.ars.commons.model.CustomerId;
import mw.ars.commons.model.FlightId;
import mw.ars.commons.model.ReservationId;
import mw.ars.commons.model.SeatNumber;

import java.time.LocalDateTime;

@Value
public class RescheduleCommand {
  private CustomerId customerId;
  private ReservationId original;
  private FlightId newFlightId;
  private SeatNumber newSeatNumber;
  private LocalDateTime newDepartureTime;

  private RescheduleCommand(CustomerId customerId,ReservationId original, FlightId newFlightId, SeatNumber newSeatNumber, LocalDateTime newDepartureTime) {
    this.customerId = customerId;
    this.original = original;
      this.newFlightId = newFlightId;
      this.newSeatNumber = newSeatNumber;
      this.newDepartureTime = newDepartureTime;
  }

  public static RescheduleCommand of(ReservationId original,CustomerId customerId ,FlightId newFlightId, SeatNumber newSeatNumber, LocalDateTime newDepartureTime) {
    return new RescheduleCommand(customerId,original, newFlightId, newSeatNumber, newDepartureTime);
  }
}
