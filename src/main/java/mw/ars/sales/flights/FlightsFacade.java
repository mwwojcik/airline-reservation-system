package mw.ars.sales.flights;

import mw.ars.commons.model.FlightId;
import mw.ars.sales.flights.common.dto.FlightDTO;

import java.util.Optional;

public interface FlightsFacade {
    Optional<FlightDTO> findByFlightId(FlightId flightId);
}
