package hse.bank.storage;

import hse.bank.domains.BankAccount;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Account storage class.
 */
@RequiredArgsConstructor
@Component
public class AccountStorage implements Serializable {
    /**
     * List of accounts.
     */
    @Getter
    @Setter
    private static List<BankAccount> accounts = new ArrayList<>();

    /**
     * Adds user.
     *
     * @param account Bank account to add.
     */
    public static void addUser(BankAccount account) {
        accounts.add(account);
    }

    /**
     * Get user.
     *
     * @param id Id to get user from.
     * @return Returns user.
     */
    public static BankAccount getUserById(int id) {
        return accounts.stream().filter(account -> account.getId() == id).findFirst().orElse(null);
    }

    /**
     * Delete user by id.
     *
     * @param id Id to delete user.
     */
    public static void deleteUserById(int id) {
        accounts.removeIf(account -> account.getId() == id);
    }

}
