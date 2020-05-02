package mw.ars.reservations.reservation.infrastructure.testapp;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ReservationInMemoryTestConfiguration.class)

public class ReservationInMemoryTestApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .parent(ReservationInMemoryTestApplication.class)
                .web(WebApplicationType.SERVLET).run(args);
    }
}
