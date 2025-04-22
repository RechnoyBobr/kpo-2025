package hse.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger config class.
 */
@Configuration
public class SwaggerConfig {
    /**
     * Custom OpenAPI.
     *
     * @return OpenAPI
     */
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
            .info(new Info()
                .title("HSE Zoo API")
                .version("1.0")
                .description("API для управления зоопарком"));
    }
}
