package mw.ars.reservations.reservation.model;

import mw.ars.reservations.reservation.common.model.CustomerId;
import mw.ars.reservations.reservation.common.model.FligtId;
import org.javamoney.moneta.Money;

import javax.money.Monetary;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

class ReservationFixture {

  public static Reservation simple() { return reservation(0,0,0); }



  private static Reservation reservation(int lockedSoFar, int reservedThisMonth, int rescheduledSoFar) {
    return Reservation.of(
            CustomerId.of(UUID.randomUUID()),
            FligtId.of(UUID.randomUUID()),
            LocalDateTime.now(),
            Money.of(new BigDecimal(10), Monetary.getCurrency("USD")),
            Reservation.CurrentlyLocked.of(lockedSoFar),
            Reservation.ReservedThisMonth.of(reservedThisMonth),
            Reservation.RescheduledSoFar.of(rescheduledSoFar));

  }

}
