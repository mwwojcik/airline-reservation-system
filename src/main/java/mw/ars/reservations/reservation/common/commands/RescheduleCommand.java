package mw.ars.reservations.reservation.common.commands;

import mw.ars.commons.model.FlightId;
import mw.ars.commons.model.ReservationId;
import mw.ars.commons.model.SeatNumber;

import java.time.LocalDateTime;

public class RescheduleCommand {
  private ReservationId original;
  private FlightId newFlightId;
  private SeatNumber newSeatNumber;
  private LocalDateTime newDepartureTime;

  private RescheduleCommand(ReservationId original, FlightId newFlightId, SeatNumber newSeatNumber, LocalDateTime newDepartureTime) {
    this.original = original;
      this.newFlightId = newFlightId;
      this.newSeatNumber = newSeatNumber;
      this.newDepartureTime = newDepartureTime;
  }

  public static RescheduleCommand of(ReservationId original, FlightId newFlightId, SeatNumber newSeatNumber, LocalDateTime newDepartureTime) {
    return new RescheduleCommand(original, newFlightId, newSeatNumber, newDepartureTime);
  }
}
