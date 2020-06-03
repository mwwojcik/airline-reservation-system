package mw.ars.reservations.reservation.infrastructure.testapp;

import mw.ars.reservations.reservation.infrastructure.ReservationConfiguration;
import mw.ars.sales.flights.infrastructure.FlightsConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "mw.ars.reservations.reservation.infrastructure")
@Import({LocalMongoDBTestConfiguration.class})
public class LocalMongoDbTestApplication {
	public static void main(String[] args) {
			new SpringApplicationBuilder()
				.parent(LocalMongoDbTestApplication.class)
				.web(WebApplicationType.SERVLET).run(args);
	}
}
