package mw.ars.reservations.reservation.common.commands;

import mw.ars.commons.model.ReservationId;

public class CancelByResrvationId{
    private ReservationId reservationId;

    private CancelByResrvationId(ReservationId reservationId){
        this.reservationId=reservationId;
    }

    public static CancelByResrvationId of(ReservationId reservationId){
        return new CancelByResrvationId(reservationId);
    }
}