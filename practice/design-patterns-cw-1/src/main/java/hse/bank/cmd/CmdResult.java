package hse.bank.cmd;

import hse.bank.domains.BankAccount;
import hse.bank.domains.Category;
import hse.bank.domains.Operation;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * Cmd result.
 *
 * @param <T> Type of data
 */
public class CmdResult<T> {
    /**
     * Data.
     */
    private final T data;
    /**
     * Error message.
     */
    @Getter
    private final String error;

    /**
     * Constructor.
     *
     * @param data  Data
     * @param error Error
     */
    public CmdResult(T data, String error) {
        this.data = data;
        this.error = error;
    }

    /**
     * Static constructor.
     *
     * @return Cmd result.
     */
    public static CmdResult<Void> success() {
        return new CmdResult<>(null, null);
    }

    /**
     * Static constructor.
     *
     * @return Cmd result.
     */
    public static <R> CmdResult<R> failure(String error) {
        return new CmdResult<>(null, error);
    }

    /**
     * Checks if successful result.
     *
     * @return Boolean
     */
    public boolean isSuccess() {
        return error == null;
    }

    /**
     * Get bank account.
     *
     * @return Bank account
     */
    public BankAccount getBankAccount() {
        if (data instanceof BankAccount) {
            return (BankAccount) data;
        }
        return null;
    }

    /**
     * Get operation.
     *
     * @return Operation
     */
    public Operation getOperation() {
        if (data instanceof Operation) {
            return (Operation) data;
        }
        return null;
    }

    /**
     * Get category.
     *
     * @return Category
     */
    public Category getCategory() {
        if (data instanceof Category) {
            return (Category) data;
        }
        return null;
    }

    /**
     * Get all categories.
     *
     * @return Categories
     */
    public List<Category> getCategoriesList() {
        List<Category> categories = new ArrayList<>(((List<?>) data).size());
        ((List<?>) data).forEach(category -> {
            if (category instanceof Category) {
                categories.add((Category) category);
            }
        });
        return categories;
    }

}