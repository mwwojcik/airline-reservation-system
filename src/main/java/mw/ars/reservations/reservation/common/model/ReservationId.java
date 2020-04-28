package mw.ars.reservations.reservation.common.model;

import java.util.UUID;

public class ReservationId {
    private UUID id;

    private ReservationId(UUID id){
        this.id=id;
    }

    public static ReservationId of(UUID id){
        return new ReservationId(id);
    }
}
