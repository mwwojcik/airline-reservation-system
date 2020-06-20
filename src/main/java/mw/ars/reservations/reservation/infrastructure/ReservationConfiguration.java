package mw.ars.reservations.reservation.infrastructure;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import mw.ars.reservations.reservation.ReservationAppService;
import mw.ars.reservations.reservation.ReservationFacade;
import mw.ars.reservations.reservation.ReservationRepository;
import mw.ars.reservations.reservation.infrastructure.db.ReservationRepositoryDB;
import mw.ars.flights.FlightsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "mw.ars.reservations.reservation.infrastructure.db")
public class ReservationConfiguration {
  @Bean
  public ReservationRepository createRepository(ReservationRepositoryDB repoDB) {
    return new DefaultReservationRepository(repoDB);
  }

  @Bean
  public ReservationAppService createService(
      ReservationRepository repo, FlightsFacade flightsFacade) {
    return new DefaultReservationAppService(repo, flightsFacade);
  }

  @Bean
  public ReservationFacade createFacade(ReservationAppService service) {
    return new DefaultReservationFacade(service);
  }
  /*
   * Use the standard Mongo driver API to create a com.mongodb.MongoClient instance.
   */
  public @Bean MongoClient mongoClient() {
    return MongoClients.create("mongodb://localhost:28017");
  }

  public @Bean MongoDbFactory mongoDbFactory(@Autowired MongoClient client) {
    return new SimpleMongoClientDbFactory(client, "reservations");
  }
}
