package mw.ars.commons.model;

import lombok.Value;

import java.util.UUID;

@Value
public class FlightId {
    private UUID id;

    private FlightId(UUID id){
        this.id=id;
    }

    public static FlightId of(UUID id){
        return new FlightId(id);
    }
}
