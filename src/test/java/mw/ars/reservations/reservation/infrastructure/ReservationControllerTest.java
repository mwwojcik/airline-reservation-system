package mw.ars.reservations.reservation.infrastructure;

import mw.ars.commons.model.ReservationId;
import mw.ars.reservations.reservation.ReservationFacade;
import mw.ars.reservations.reservation.infrastructure.inmemorydb.ReservationInMemoryTestApplication;
import mw.ars.reservations.reservation.model.ReservationFixture;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ReservationInMemoryTestApplication.class})
@AutoConfigureMockMvc
class ReservationControllerTest {
  @Autowired private MockMvc mockMvc;
  @MockBean private ReservationFacade reservationFacade;

  @DisplayName("GET on /api/reservations/{Id} then reservation is returned")
  @Test
  void getOnApiReservationsIdThenReservationIsReturned() throws Exception {
    // given
    Mockito.when(reservationFacade.findDetailsByReservationId(Mockito.any()))
        .thenReturn(Optional.of(ReservationFixture.dto()));
    // when
    mockMvc
        .perform(
            MockMvcRequestBuilders.get(String.format("/api/reservations/%s", UUID.randomUUID()))
            // then
            )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.reservationId", Matchers.notNullValue()));
  }

  @DisplayName("GET on /api/reservations with given customerId and flightId")
  @Test
  void getOnApiReservationsWithGivenCustomerIdAndFlightId() throws Exception {
    // given
    Mockito.when(reservationFacade.findByFlightId(Mockito.any()))
        .thenReturn(List.of(ReservationFixture.dto(), ReservationFixture.dto()));
    // when
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/reservations")
                .param("customerId", UUID.randomUUID().toString())
                .param("flightId", UUID.randomUUID().toString())
            // then
            )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
  }

  @DisplayName("POST on /api/reservations then Reservation is created")
  @Test
  void postOnApiReservationsThenReservationIsCreated() throws Exception {
    // given
    when(reservationFacade.create(Mockito.any())).thenReturn(ReservationId.of(UUID.randomUUID()));
    // when
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/reservations/create")
                .param("customerId", UUID.randomUUID().toString())
                .param("flightId", UUID.randomUUID().toString()))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @DisplayName("PUT on /api/reservations/{id}/hold should hold the Reservation")
  @Test
  void putOnApiReservationsIdHoldShouldHoldTheReservation() throws Exception {
    // given
    Mockito.doNothing().when(reservationFacade).holdOn(Mockito.any());
    // when
    mockMvc
        .perform(
            MockMvcRequestBuilders.put(
                String.format("/api/reservations/%s/hold", UUID.randomUUID().toString())))
        .andExpect(MockMvcResultMatchers.status().isAccepted());
  }
}

/*
package it.stacja.springworkshop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TweetController.class)
@WithMockUser
public class TweetApiWebTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TweetService tweetService;

	// @formatter:off
	@DisplayName(
		"when GET on /api/tweets, " +
		"then 200 status is returned and a list of tweets is returned" +
		" as well"
	)
	// @formatter:on
	@Test
	void okStatus() throws Exception {
		// given
		when(tweetService.allTweets()).thenReturn(List
			.of(tweetWithTitle("title0"), tweetWithTitle("title1")
				, tweetWithTitle("title2")));

		// when
		mockMvc.perform(get("/api/tweets"))

			// then
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._embedded.tweetList",
				hasSize(3)));
		verify(tweetService).allTweets();
	}

	// @formatter:off
	@DisplayName(
		"when POST on /api/tweets with tweet data, " +
		"then create command is invoked with the same data"
	)
	// @formatter:on
	@Test
	void create() throws Exception {
		// given
		// @formatter:off
		String tweetJson =
			"{ \"title\": \"My First tweet\" }";
		// @formatter:on

		// when
		mockMvc.perform(post("/api/tweets").content(tweetJson)
			.contentType(MediaType.APPLICATION_JSON));

		// then
		Tweet tweet = tweetWithTitle("My First tweet");
		verify(tweetService).create(tweet);
	}

	// @formatter:off
	@DisplayName(
		"when GET on /api/tweets with param title = 'title1', " +
		"then only tweet with title 'title1' is returned"
	)
	// @formatter:on
	@Test
	void searchByTitle() throws Exception {
		// given
		when(tweetService.findByTitle("title1"))
			.thenReturn(List.of(tweetWithTitle("title1")));

		// when
		mockMvc.perform(get("/api/tweets").param("title", "title1"))

			// then
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].title", is("title1")));
		verify(tweetService).findByTitle("title1");
	}

	private Tweet tweetWithTitle(String title) {
		return TweetTestUtils.tweetWithTitle(title);
	}
}

package it.stacja.springworkshop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TweetController.class)
@WithMockUser
public class TweetApiWebTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TweetService tweetService;

	// @formatter:off
	@DisplayName(
		"when GET on /api/tweets, " +
		"then 200 status is returned and a list of tweets is returned" +
		" as well"
	)
	// @formatter:on
	@Test
	void okStatus() throws Exception {
		// given
		when(tweetService.allTweets()).thenReturn(List
			.of(tweetWithTitle("title0"), tweetWithTitle("title1")
				, tweetWithTitle("title2")));

		// when
		mockMvc.perform(get("/api/tweets"))

			// then
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._embedded.tweetList",
				hasSize(3)));
		verify(tweetService).allTweets();
	}

	// @formatter:off
	@DisplayName(
		"when POST on /api/tweets with tweet data, " +
		"then create command is invoked with the same data"
	)
	// @formatter:on
	@Test
	void create() throws Exception {
		// given
		// @formatter:off
		String tweetJson =
			"{ \"title\": \"My First tweet\" }";
		// @formatter:on

		// when
		mockMvc.perform(post("/api/tweets").content(tweetJson)
			.contentType(MediaType.APPLICATION_JSON));

		// then
		Tweet tweet = tweetWithTitle("My First tweet");
		verify(tweetService).create(tweet);
	}

	// @formatter:off
	@DisplayName(
		"when GET on /api/tweets with param title = 'title1', " +
		"then only tweet with title 'title1' is returned"
	)
	// @formatter:on
	@Test
	void searchByTitle() throws Exception {
		// given
		when(tweetService.findByTitle("title1"))
			.thenReturn(List.of(tweetWithTitle("title1")));

		// when
		mockMvc.perform(get("/api/tweets").param("title", "title1"))

			// then
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].title", is("title1")));
		verify(tweetService).findByTitle("title1");
	}

	private Tweet tweetWithTitle(String title) {
		return TweetTestUtils.tweetWithTitle(title);
	}
}

* */
