package mw.ars;

import mw.ars.reservations.reservation.infrastructure.ReservationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ReservationConfiguration.class)
public class ArsApplication {
	public static void main(String[] args) {
			new SpringApplicationBuilder()
				.parent(ArsApplication.class)
				.web(WebApplicationType.SERVLET).run(args);
	}
}
