package hse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("hse.domains")
public class FileStoringService {

    public static void main(String[] args) {
        SpringApplication.run(FileStoringService.class, args);
    }
}
