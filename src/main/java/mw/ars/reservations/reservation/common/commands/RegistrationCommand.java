package mw.ars.reservations.reservation.common.commands;

import lombok.Value;
import mw.ars.commons.model.FlightId;
import mw.ars.commons.model.ReservationId;
import mw.ars.commons.model.SeatNumber;

import java.time.LocalDateTime;

@Value
public class RegistrationCommand{

    private ReservationId reservationId;
    private SeatNumber withSeat;
    private FlightId flightId;
    private LocalDateTime departureTime;



    private RegistrationCommand(ReservationId reservationId,FlightId flightId,LocalDateTime departureTime,SeatNumber withSeat){
        this.reservationId=reservationId;
        this.flightId = flightId;
        this.departureTime = departureTime;
        this.withSeat = withSeat;
    }

    public static RegistrationCommand of(ReservationId reservationId,FlightId flightId,LocalDateTime departureTime,SeatNumber withSeat){
        return new RegistrationCommand(reservationId,flightId,departureTime,withSeat);
    }
}