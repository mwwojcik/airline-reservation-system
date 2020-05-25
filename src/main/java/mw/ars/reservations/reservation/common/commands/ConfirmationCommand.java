package mw.ars.reservations.reservation.common.commands;

import lombok.Value;
import mw.ars.commons.model.ReservationId;
@Value
public class ConfirmationCommand{
    private ReservationId reservationId;

    private ConfirmationCommand(ReservationId reservationId){
        this.reservationId=reservationId;
    }

    public static ConfirmationCommand of(ReservationId reservationId){
        return new ConfirmationCommand(reservationId);
    }
}