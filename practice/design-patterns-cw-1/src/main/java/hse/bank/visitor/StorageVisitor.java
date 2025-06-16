package hse.bank.visitor;

import hse.bank.domains.BankAccount;
import hse.bank.domains.Category;
import hse.bank.domains.Operation;
import hse.bank.storage.AccountStorage;
import hse.bank.storage.CategoryStorage;
import hse.bank.storage.OperationStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class StorageVisitor {

    public static Stream<BankAccount> walkBankAccountStorage() {
        return AccountStorage.getAccounts().stream();
    }

    public static Stream<Operation> walkOperationStorage() {
        return OperationStorage.getOperations().stream();

    }

    public static Stream<Category> walkCategoryStorage() {
        return CategoryStorage.getCategories().stream();
    }
}
