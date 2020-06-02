package mw.ars.reservations.reservation.infrastructure.testapp;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;


@SpringBootApplication(scanBasePackages = "mw.ars.reservations.reservation.infrastructure")
@Import({ReservationLocalMongoDBConfiguration.class})
public class ReservationTestApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .parent(ReservationTestApplication.class)
                .web(WebApplicationType.SERVLET).run(args);
    }
}
