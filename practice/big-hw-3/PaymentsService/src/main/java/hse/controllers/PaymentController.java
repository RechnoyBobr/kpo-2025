package hse.controllers;

import hse.services.PaymentsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentsService service;

    @PostMapping("/create")
    @Operation(summary = "Creates an account")
    public ResponseEntity<String> createAccount(@RequestBody Integer userId) {
        if (!service.createAccount(userId)) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(userId.toString());
    }

    @PostMapping("/topup/{id}")
    @Operation(summary = "Tops up the balance of account")
    public ResponseEntity<String> topUp(@PathVariable Integer id, @RequestBody Double amount) {
        if (!service.topUp(id, amount)) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(id.toString());
    }

    @GetMapping("/balance/{id}")
    @Operation(summary = "Get balance of user")
    public ResponseEntity<String> getBalance(@PathVariable Integer id) {
        Double res = service.getBalance(id);
        if (res < 0) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(res.toString());
    }

}
