package mw.ars.reservations.reservation.common.commands;

import lombok.Value;
import mw.ars.commons.model.CustomerId;
import mw.ars.commons.model.FlightId;

@Value
public class CreateReservationCommand{
    private CustomerId customerId;
    private FlightId flightId;

    private CreateReservationCommand(CustomerId customerId, FlightId flightId){
        this.customerId=customerId;
        this.flightId = flightId;
    }

    public static CreateReservationCommand of(CustomerId customerId, FlightId flightId){
        return new CreateReservationCommand(customerId,flightId);
    }
}
