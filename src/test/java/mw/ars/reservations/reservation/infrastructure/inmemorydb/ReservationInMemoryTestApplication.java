package mw.ars.reservations.reservation.infrastructure.inmemorydb;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;


@SpringBootApplication(scanBasePackages = "mw.ars.reservations.reservation.infrastructure")
@Import({ReservationInMemoryTestConfiguration.class})
public class ReservationInMemoryTestApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .parent(ReservationInMemoryTestApplication.class)
                .web(WebApplicationType.SERVLET).run(args);
    }
}
