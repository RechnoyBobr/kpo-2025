package hse;

import hse.application.AnimalTransferService;
import hse.application.FeedingOrganizationService;
import hse.application.ZooStatisticsService;
import hse.domain.Animal;
import hse.domain.Enclosure;
import hse.infrastructure.facade.ZooFacade;
import hse.valueObjects.AnimalType;
import hse.valueObjects.EnclosureParams;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class.
 */
@SpringBootApplication
public class Main {
    /**
     * Main function.
     *
     * @param args Main args
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}