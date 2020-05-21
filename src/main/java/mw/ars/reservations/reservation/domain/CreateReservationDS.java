package mw.ars.reservations.reservation.domain;

import mw.ars.commons.model.Result;
import mw.ars.reservations.reservation.common.commands.CreateReservationCommand;
import mw.ars.reservations.reservation.model.InitialReservation;

public class CreateReservationDS {
    public static Result initialReservation(int reservedThisMonth,CreateReservationCommand command) {
        return InitialReservation.create(reservedThisMonth,command.getFligtId(),command.getCustomerId());
    }
}
