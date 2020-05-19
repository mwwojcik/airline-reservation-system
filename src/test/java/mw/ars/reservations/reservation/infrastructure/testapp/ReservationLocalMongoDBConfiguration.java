package mw.ars.reservations.reservation.infrastructure.testapp;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import mw.ars.reservations.reservation.ReservationFacade;
import mw.ars.reservations.reservation.ReservationRepository;
import mw.ars.reservations.reservation.ReservationService;
import mw.ars.reservations.reservation.infrastructure.DefaultReservationFacade;
import mw.ars.reservations.reservation.infrastructure.DefaultReservationRepository;
import mw.ars.reservations.reservation.infrastructure.DefaultReservationService;
import mw.ars.reservations.reservation.infrastructure.ReservationConfiguration;
import mw.ars.reservations.reservation.infrastructure.db.ReservationRepositoryDB;
import mw.ars.reservations.reservation.infrastructure.testapp.ReservationInMemoryTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Profile("embedded")
@EnableMongoRepositories(basePackages = "mw.ars.reservations.reservation.infrastructure.db")
public class ReservationLocalMongoDBConfiguration {
  @Bean
  public ReservationRepository createRepository(ReservationRepositoryDB repoDB) {
    return new DefaultReservationRepository(repoDB);
  }

  @Bean
  public ReservationService createService(ReservationRepository repo) {
    return new DefaultReservationService(repo);
  }

  @Bean
  public ReservationFacade createFacade(ReservationService service) {
    return new DefaultReservationFacade(service);
  }
}
