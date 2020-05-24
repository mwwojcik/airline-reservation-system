package mw.ars.sales.flights.infrastructure;

import mw.ars.commons.model.FlightId;
import mw.ars.sales.flights.FlightsFacade;
import mw.ars.sales.flights.common.dto.FlightDTO;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class DefaultFlightsFacade implements FlightsFacade {
    @Override
    public Optional<FlightDTO> findByFlightId(FlightId flightId) {
        return Optional.of(FlightDTO.from(FlightId.of(UUID.randomUUID()), LocalDateTime.now(), Money.of(100,"USD")));
    }
}
