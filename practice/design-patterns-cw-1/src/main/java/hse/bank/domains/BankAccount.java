package hse.bank.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Bank account class.
 */
@AllArgsConstructor
public class BankAccount{
    /**
     * Account id.
     */
    @Getter
    private final int id;

    /**
     * Account name.
     */
    @Getter
    private String name;

    /**
     * Account balance.
     */
    @Getter
    @Setter
    private double balance;

}
