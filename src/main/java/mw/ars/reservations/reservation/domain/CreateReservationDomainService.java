package mw.ars.reservations.reservation.domain;

import mw.ars.commons.model.Result;
import mw.ars.reservations.reservation.common.commands.CreateReservationCommand;
import mw.ars.reservations.reservation.model.InitialReservation;

public class CreateReservationDomainService {
    public static InitialReservation create(int reservedThisMonth, CreateReservationCommand command) {
      var res=InitialReservation.create(reservedThisMonth,command.getFlightId(),command.getCustomerId());
      if(res.isFailure()){
          throw new IllegalStateException("Reservation not created!");
      }
      return (InitialReservation) res.returned();
    }
}
