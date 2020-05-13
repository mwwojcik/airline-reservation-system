package mw.ars.reservations.reservation.common.commands;

import mw.ars.reservations.reservation.common.model.ReservationId;

public class FindByReservationIdCommnad {
    private ReservationId reservationId;

    private FindByReservationIdCommnad(ReservationId reservationId){
        this.reservationId=reservationId;
    }

    public static FindByReservationIdCommnad of(ReservationId reservationId){
        return new FindByReservationIdCommnad(reservationId);
    }
}
