package mw.ars.flights;

import mw.ars.commons.model.FlightId;
import mw.ars.flights.common.dto.FlightDTO;

import java.util.Optional;

public interface FlightsFacade {
    Optional<FlightDTO> findByFlightId(FlightId flightId);
}
