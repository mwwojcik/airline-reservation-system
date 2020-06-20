package mw.ars;

import mw.ars.reservations.reservation.infrastructure.ReservationConfiguration;
import mw.ars.flights.infrastructure.FlightsConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "mw.ars.reservations.reservation.infrastructure")
@Import({ReservationConfiguration.class, FlightsConfiguration.class})
public class ArsApplication {
	public static void main(String[] args) {
			new SpringApplicationBuilder()
				.parent(ArsApplication.class)
				.web(WebApplicationType.SERVLET).run(args);
	}
}
