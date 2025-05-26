package hse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Main java class.
 */
@SpringBootApplication
@EntityScan("hse.domains")
public class FileStoringService {

    /**
     * Main method.
     *
     * @param args Args
     */
    public static void main(String[] args) {
        SpringApplication.run(FileStoringService.class, args);
    }
}
