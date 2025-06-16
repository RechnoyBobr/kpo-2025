package hse.bank.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Operation category class.
 */
@AllArgsConstructor
public class Category {

    /**
     * Category id.
     */
    @Getter
    private int id;

    /**
     * Type of transaction (income or expense - true or false)
     */
    @Getter
    private boolean isPositive;

    /**
     * Category name. Implemented with enum
     */
    @Getter
    private String name;

}
