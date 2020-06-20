package mw.ars.flights.infrastructure;

import mw.ars.flights.FlightsFacade;
import org.springframework.context.annotation.Bean;

public class FlightsConfiguration {
    @Bean
    FlightsFacade flightsFacade(){
        return new DefaultFlightsFacade();
    }
}
