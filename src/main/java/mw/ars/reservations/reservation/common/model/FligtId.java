package mw.ars.reservations.reservation.common.model;

import lombok.Value;

import java.util.UUID;

@Value
public class FligtId{
    private UUID id;

    private FligtId(UUID id){
        this.id=id;
    }

    public static FligtId of(UUID id){
        return new FligtId(id);
    }
}
