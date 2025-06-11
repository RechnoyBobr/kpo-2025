package hse;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "gateway", ignoreUnknownFields = false)
public record ApiConfig(@NotEmpty String orderServiceUrl, @NotEmpty String paymentsServiceUrl) {
}
