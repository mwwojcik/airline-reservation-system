package mw.ars.reservations.reservation.common.commands;

import lombok.Value;
import mw.ars.reservations.reservation.common.model.CustomerId;
import mw.ars.reservations.reservation.common.model.FligtId;
@Value
public class FindByFlightIdCommand{
    private CustomerId customerId;
    private FligtId fligtId;

    private FindByFlightIdCommand(CustomerId customerId, FligtId fligtId){
        this.customerId=customerId;
        this.fligtId = fligtId;
    }

    public static FindByFlightIdCommand of(CustomerId customerId, FligtId flightId){
        return new FindByFlightIdCommand(customerId,flightId);
    }
}
