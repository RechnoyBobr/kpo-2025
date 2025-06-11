package hse.Controller;

import hse.ApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

/**
 * Gateway controller.
 */
@RestController
@RequestMapping("/api")
public class GatewayController {

    /**
     * Client for managing orders.
     */
    private final RestClient ordersClient;

    /**
     * Client for managing user accounts.
     */
    private final RestClient paymentsClient;

    /**
     * Constructor.
     * @param config Config of spring application
     */
    public GatewayController(ApiConfig config) {
        paymentsClient = RestClient.builder().baseUrl(config.paymentsServiceUrl()).build();
        ordersClient = RestClient.builder().baseUrl(config.orderServiceUrl()).build();
    }

    /**
     * Create user account.
     * @param userId User account id.
     * @return Response.
     */
    @PostMapping("/create/account")
    @Operation(summary = "Create account")
    public ResponseEntity<String> createAccount(@RequestBody Integer userId) {
        return paymentsClient.post().uri("/create").body(userId).retrieve().toEntity(String.class);
    }

    /**
     * Topup balance.
     * @param id ID of account to top up.
     * @param amount Amount to top up.
     * @return Response entity.
     */
    @PostMapping("/topup/{id}")
    @Operation(summary = "Topup balance")
    public ResponseEntity<String> topup(@PathVariable Integer id, @RequestBody Integer amount) {
        return paymentsClient.post().uri("/topup/" + id.toString()).body(amount).retrieve().toEntity(String.class);
    }

    @PostMapping("/create/order")
    public ResponseEntity<String> createOrder()
}
