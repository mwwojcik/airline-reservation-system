package mw.ars.reservations.reservation.common.commands;

import lombok.Getter;
import mw.ars.commons.model.ReservationId;

public class FindByReservationIdCommand {

    @Getter private ReservationId reservationId;

    private FindByReservationIdCommand(ReservationId reservationId){
        this.reservationId=reservationId;
    }

    public static FindByReservationIdCommand of(ReservationId reservationId){
        return new FindByReservationIdCommand(reservationId);
    }
}
