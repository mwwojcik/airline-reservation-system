package mw.ars.reservations.reservation.model;

import mw.ars.reservations.reservation.common.model.FligtId;
import mw.ars.reservations.reservation.common.model.SeatNumber;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Fail;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

class RescheduleServiceTest {
  private RescheduleService service = new RescheduleService();

  @DisplayName("Should reschedule reservation")
  @Test
  void shouldRescheduleReservation() {
    // given
    var original = ReservationFixture.inConfirmedState();
    // when
    var rescheduled =
        service.reschedule(
            original,
            FligtId.of(UUID.randomUUID()),
            SeatNumber.of(5),
            LocalDateTime.now().plusDays(5),
            Money.of(19, "USD"));

    Assertions.assertThat(rescheduled.getId()).isNotEqualTo(original.getId());
    Assertions.assertThat(rescheduled.getParentResId()).isEqualTo(original.getId());

  }
}
