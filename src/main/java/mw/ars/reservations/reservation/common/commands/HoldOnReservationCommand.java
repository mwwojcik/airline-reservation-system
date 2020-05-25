package mw.ars.reservations.reservation.common.commands;

import lombok.Value;
import mw.ars.commons.model.ReservationId;
import mw.ars.commons.model.SeatNumber;

import java.time.LocalDateTime;

@Value
public class HoldOnReservationCommand {
    private ReservationId reservationId;


    private HoldOnReservationCommand(ReservationId reservationId){
        this.reservationId=reservationId;
    }

    public static HoldOnReservationCommand of(ReservationId reservationId){
        return new HoldOnReservationCommand(reservationId);
    }
}