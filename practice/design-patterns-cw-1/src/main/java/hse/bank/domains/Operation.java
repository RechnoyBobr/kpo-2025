package hse.bank.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;


@AllArgsConstructor
@Getter
public class Operation {

    /**
     * Operation id.
     */
    private int id;

    /**
     * Type of transaction (income or expense) - (true or false)
     */
    private boolean type;

    /**
     * Account of transaction.
     */
    private BankAccount account;

    /**
     * Transaction amount.
     */
    private double amount;

    /**
     * Date of transaction.
     */
    private LocalDateTime date;

    /**
     * Category of transaction.
     */
    private Category category;

    /**
     * Optional description string.
     */
    private Optional<String> description;

}
