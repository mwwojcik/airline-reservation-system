package mw.ars.flights;

import mw.ars.commons.model.FlightId;
import mw.ars.flights.common.dto.FlightDTO;
import mw.ars.flights.model.FlightDefinitionCommand;

import java.util.Optional;

public interface FlightsPlanService {
    Optional<FlightDTO> findByFlightId(FlightId flightId);
    FlightDTO createFlightDefinition(FlightDefinitionCommand command);
}
