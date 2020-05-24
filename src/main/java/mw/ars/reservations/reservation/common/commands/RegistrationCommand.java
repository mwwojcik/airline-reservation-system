package mw.ars.reservations.reservation.common.commands;

import lombok.Value;
import mw.ars.commons.model.ReservationId;
import mw.ars.commons.model.SeatNumber;

@Value
public class RegistrationCommand{

    private ReservationId reservationId;
    private SeatNumber withSeat;


    private RegistrationCommand(ReservationId reservationId,SeatNumber withSeat){
        this.reservationId=reservationId;
        this.withSeat = withSeat;
    }

    public static RegistrationCommand of(ReservationId reservationId,SeatNumber withSeat){
        return new RegistrationCommand(reservationId,withSeat);
    }
}