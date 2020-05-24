package mw.ars.reservations.reservation.common.commands;

import lombok.Value;
import mw.ars.commons.model.CustomerId;
import mw.ars.commons.model.FlightId;
@Value
public class FindByFlightIdCommand{
    private CustomerId customerId;
    private FlightId flightId;

    private FindByFlightIdCommand(CustomerId customerId, FlightId flightId){
        this.customerId=customerId;
        this.flightId = flightId;
    }

    public static FindByFlightIdCommand of(CustomerId customerId, FlightId flightId){
        return new FindByFlightIdCommand(customerId,flightId);
    }
}
