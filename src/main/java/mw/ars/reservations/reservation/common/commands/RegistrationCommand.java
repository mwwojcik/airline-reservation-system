package mw.ars.reservations.reservation.common.commands;

import lombok.Value;
import mw.ars.reservations.reservation.common.model.ReservationId;

@Value
public class RegistrationCommand{
    private ReservationId reservationId;

    private RegistrationCommand(ReservationId reservationId){
        this.reservationId=reservationId;
    }

    public static RegistrationCommand of(ReservationId reservationId){
        return new RegistrationCommand(reservationId);
    }
}