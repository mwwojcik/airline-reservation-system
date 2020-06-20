package mw.ars.reservations.reservation.domain;

import mw.ars.reservations.reservation.common.commands.RegistrationCommand;
import mw.ars.reservations.reservation.model.IdentifiedReservation;
import mw.ars.reservations.reservation.model.InitialReservation;
import mw.ars.reservations.reservation.model.RegisteredReservation;
import mw.ars.flights.common.dto.FlightDTO;
import org.javamoney.moneta.Money;

import java.util.Optional;

public class RegisterReservationDomainService {

    public RegisterReservationDomainService() {
    }


    public static RegisteredReservation register(IdentifiedReservation reservation, RegistrationCommand command, FlightDTO flightDTO) {
        var initial = toInitial(reservation).orElseThrow(() -> new IllegalStateException("Reservation should be NEW!"));
        var res=initial.register(command.getWithSeat(),calculate(flightDTO.getPrice()),flightDTO.getDepartureDate());
        if(res.isFailure()){
            throw new IllegalArgumentException("Reservation not registered!");
        }
    return ((RegisteredReservation) res.returned());
    }

     private static Optional<InitialReservation> toInitial(IdentifiedReservation reservation) {
        return reservation instanceof InitialReservation ? Optional.of((InitialReservation)reservation):Optional.empty();
    }

    private static Money calculate(Money initialPrice) {
        //todo price policy
     return Money.of(200,"USD") ;
     }


}
