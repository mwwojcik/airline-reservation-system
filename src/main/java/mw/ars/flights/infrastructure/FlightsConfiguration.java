package mw.ars.flights.infrastructure;

import mw.ars.flights.FlightsFacade;
import mw.ars.flights.FlightsPlanService;
import org.springframework.context.annotation.Bean;

public class FlightsConfiguration {
    @Bean
    FlightsFacade flightsFacade(FlightsPlanService flightsPlanService){
        return new DefaultFlightsFacade(flightsPlanService);
    }

    @Bean
    FlightsPlanService flightsPlanService(){
        return new DefaultFlightsPlanAppService();
    }
}
