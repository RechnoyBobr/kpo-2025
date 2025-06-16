package hse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PaymentsApp {
    public static void main(String[] args) {
        SpringApplication.run(PaymentsApp.class, args);
    }
}
