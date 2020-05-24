package mw.ars.commons.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;
@EqualsAndHashCode(of = "id")
@Getter
public class ReservationId {
    private UUID id;

    private ReservationId(UUID id){
        this.id=id;
    }

    public static ReservationId of(UUID id){
        return new ReservationId(id);
    }
}
