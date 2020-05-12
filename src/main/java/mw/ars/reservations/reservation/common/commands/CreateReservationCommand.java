package mw.ars.reservations.reservation.common.commands;

import lombok.Value;
import mw.ars.reservations.reservation.common.model.CustomerId;
import mw.ars.reservations.reservation.common.model.FligtId;

@Value
public class CreateReservationCommand{
    private CustomerId customerId;
    private FligtId fligtId;

    private CreateReservationCommand(CustomerId customerId, FligtId fligtId){
        this.customerId=customerId;
        this.fligtId = fligtId;
    }

    public static CreateReservationCommand of(CustomerId customerId, FligtId flightId){
        return new CreateReservationCommand(customerId,flightId);
    }
}
