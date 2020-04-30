package mw.ars.reservations.reservation.common.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

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
