package mw.ars.sales.flights.infrastructure;

import mw.ars.sales.flights.FlightsFacade;
import org.springframework.context.annotation.Bean;

public class FlightsConfiguration {
    @Bean
    FlightsFacade flightsFacade(){
        return new DefaultFlightsFacade();
    }
}
