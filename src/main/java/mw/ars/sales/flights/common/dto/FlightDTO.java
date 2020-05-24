package mw.ars.sales.flights.common.dto;

import lombok.Value;
import mw.ars.commons.model.FlightId;
import mw.ars.sales.flights.FlightsFacade;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;

@Value
public class FlightDTO {
    FlightId flightId;
    LocalDateTime departureDate;
    Money price;

    private FlightDTO(FlightId flightId, LocalDateTime departureDate, Money price) {
        this.flightId = flightId;
        this.departureDate = departureDate;
        this.price = price;
    }

    public static FlightDTO from(FlightId flightId, LocalDateTime departureDate, Money price) {
     return new FlightDTO(flightId,departureDate,price) ;
     }

}
