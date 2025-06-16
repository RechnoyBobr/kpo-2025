package hse.bank.factories;

import hse.bank.domains.BankAccount;
import hse.bank.records.BankAccountData;
import org.springframework.stereotype.Component;

@Component
public class BankAccountFactory {
    static int globalId = -1;

    public static BankAccount createAccount(BankAccountData data) {
        globalId++;
        return new BankAccount(globalId, data.name(), data.initialBalance());
    }

    public static BankAccount createAccountWithId(BankAccountData data, int id) {
        if (id > globalId) {
            globalId = id;
        }
        return new BankAccount(id, data.name(), data.initialBalance());
    }
    public static void flush() {
        globalId = -1;
    }
}
