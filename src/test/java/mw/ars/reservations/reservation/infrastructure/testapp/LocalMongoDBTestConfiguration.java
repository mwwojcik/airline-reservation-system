package mw.ars.reservations.reservation.infrastructure.testapp;

import mw.ars.reservations.reservation.ReservationFacade;
import mw.ars.reservations.reservation.ReservationRepository;
import mw.ars.reservations.reservation.ReservationAppService;
import mw.ars.reservations.reservation.infrastructure.DefaultReservationFacade;
import mw.ars.reservations.reservation.infrastructure.DefaultReservationRepository;
import mw.ars.reservations.reservation.infrastructure.DefaultReservationAppService;
import mw.ars.reservations.reservation.infrastructure.db.ReservationRepositoryDB;
import mw.ars.flights.FlightsFacade;
import mw.ars.flights.infrastructure.DefaultFlightsFacade;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = "mw.ars.reservations.reservation.infrastructure.db")
public class LocalMongoDBTestConfiguration {
  @Bean
  public ReservationRepository createRepository(ReservationRepositoryDB repoDB) {
    return new DefaultReservationRepository(repoDB);
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
