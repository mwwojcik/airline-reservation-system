package mw.ars.reservations.reservation.infrastructure.testapp;

import mw.ars.reservations.reservation.ReservationFacade;
import mw.ars.reservations.reservation.ReservationRepository;
import mw.ars.reservations.reservation.ReservationAppService;
import mw.ars.reservations.reservation.infrastructure.DefaultReservationFacade;
import mw.ars.reservations.reservation.infrastructure.DefaultReservationAppService;
import mw.ars.reservations.reservation.infrastructure.InMemoryReservationRepository;
import mw.ars.sales.flights.FlightsFacade;
import mw.ars.sales.flights.infrastructure.DefaultFlightsFacade;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile("inmemory")
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class
        , MongoDataAutoConfiguration.class
        , EmbeddedMongoAutoConfiguration.class})
public class ReservationInMemoryTestConfiguration {
  @Bean
  public ReservationRepository createRepository() {
    return new InMemoryReservationRepository();
  }

  @Bean
  public ReservationAppService createService(ReservationRepository repo, FlightsFacade flightsFacade) {
    return new DefaultReservationAppService(repo, flightsFacade);
  }

  @Bean
  public ReservationFacade createFacade(ReservationAppService service) {
    return new DefaultReservationFacade(service);
  }

  @Bean
  FlightsFacade createFlightFacade(){
    return new DefaultFlightsFacade() ;
  }
}
