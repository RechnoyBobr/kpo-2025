package hse.Controller;

import hse.ApiConfig;
import hse.dto.Order;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
@Slf4j
public class GatewayController {

    /**
     * Client for managing orders.
     */
    private final RestClient ordersClient;

    /**
     * Client for managing user accounts.
     */
    private final RestClient paymentsClient;

    private final ApiConfig apiConfig;

    /**
     * Constructor.
     *
     * @param config Config of spring application
     */
    @Autowired
    public GatewayController(ApiConfig config) {
        this.apiConfig = config;
        paymentsClient = RestClient.builder().baseUrl(config.paymentsServiceUrl()).build();
        ordersClient = RestClient.builder().baseUrl(config.orderServiceUrl()).build();
    }

    /**
     * Create user account.
     *
     * @param userId User account id.
     * @return Response.
     */
    @PostMapping("/create/account")
    @Operation(summary = "Create account")
    public ResponseEntity<String> createAccount(@RequestBody Integer userId) {
        System.out.println(apiConfig);
        return paymentsClient.post().uri("/create").body(userId).retrieve().toEntity(String.class);
    }

    /**
     * Topup balance.
     *
     * @param id     ID of account to top up.
     * @param amount Amount to top up.
     * @return Response entity.
     */
    @PostMapping("/topup/{id}")
    @Operation(summary = "Topup balance")
    public ResponseEntity<String> topup(@PathVariable Integer id, @RequestBody Integer amount) {
        return paymentsClient.post().uri("/topup/" + id.toString()).body(amount).retrieve().toEntity(String.class);
    }

    /**
     * Create an order.
     *
     * @param order Order dto
     * @return Response
     */
    @PostMapping("/create/order")
    @Operation(summary = "Create order")
    public ResponseEntity<String> createOrder(@Valid @RequestBody Order order) {
        return ordersClient.post().uri("/create").body(order).retrieve().toEntity(String.class);
    }

    /**
     * Get user balance.
     *
     * @param id User ID
     * @return Balance
     */
    @GetMapping("/balance/{id}")
    @Operation(summary = "Get balance of user")
    public ResponseEntity<String> getBalance(@PathVariable Integer id) {
        return paymentsClient.get().uri("/balance/" + id.toString()).retrieve().toEntity(String.class);
    }

    /**
     * List all orders.
     *
     * @return String of all user orders
     */
    @PostMapping("/list/orders")
    public ResponseEntity<String> getOrders(@RequestBody Integer userId) {
        return ordersClient.post().uri("/list/orders")
                .body(userId).retrieve().toEntity(String.class);
    }

    /**
     * Get order status.
     *
     * @param id Order id.
     * @return Order status
     */
    @GetMapping("/list/orders/{id}")
    public ResponseEntity<String> getOrder(@PathVariable Integer id) {
        return ordersClient.get().uri("/list/orders/" + id.toString())
                .retrieve().toEntity(String.class);
    }
}
