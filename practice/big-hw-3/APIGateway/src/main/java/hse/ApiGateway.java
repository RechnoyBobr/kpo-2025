package hse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApiConfig.class})
public class ApiGateway {
    public static void main(String[] args) {
        SpringApplication.run(ApiGateway.class, args);
    }
}
