package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationFacade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/** Web Rest API Test - Facade is mocked bean . Tested are only Request/Responses via REST. */
@SpringBootTest
@AutoConfigureMockMvc
class ReservationWebApiTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private ReservationFacade reservationFacade;

  @DisplayName("Web Rest API test")
  @Test
  void simpleIntegrationTest() {
    // given
    // when
    // then
    System.out.println("test");
    //        Fail.fail("Write your test");
  }
}
