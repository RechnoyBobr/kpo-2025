package hse.controllers;

import hse.dto.Order;
import hse.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    @Operation(summary = "Creates order")

    public ResponseEntity<String> createOrder(@Valid @RequestBody Order order, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(result.getAllErrors().getFirst().getDefaultMessage());
        }
        orderService.createOrder(order);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/list/orders")
    @Operation(summary = "List all orders of user")
    public ResponseEntity<String> listOrders(@RequestBody Integer userId) {
        return ResponseEntity.ok().body(orderService.getOrders(userId));
    }

    @GetMapping("/list/orders/{id}")
    @Operation(summary = "Get order information")
    public ResponseEntity<String> getOrder(@PathVariable Integer id) {
        return ResponseEntity.ok().body(orderService.getOrder(id));
    }
}
