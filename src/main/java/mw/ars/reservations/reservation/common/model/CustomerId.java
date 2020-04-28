package mw.ars.reservations.reservation.common.model;

import java.util.UUID;

public class CustomerId{
    private UUID id;

    private CustomerId(UUID id){
        this.id=id;
    }

    public static CustomerId of(UUID id){
        return new CustomerId(id);
    }
}
