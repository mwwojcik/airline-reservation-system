package mw.ars.flights.infrastructure;

import mw.ars.commons.model.FlightId;
import mw.ars.flights.FlightsFacade;
import mw.ars.flights.FlightsPlanService;
import mw.ars.flights.common.dto.FlightDTO;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class DefaultFlightsFacade implements FlightsFacade {

    private FlightsPlanService flightsPlanService;

    public DefaultFlightsFacade(FlightsPlanService flightsPlanService) {

        this.flightsPlanService = flightsPlanService;
    }

    @Override
    public Optional<FlightDTO> findByFlightId(FlightId flightId) {
        return flightsPlanService.findByFlightId(flightId);
    }

    @Override
    public FlightDTO createFlightDefinition(Object param) {
     return flightsPlanService.createFlightDefinition(null) ;
     }

}
