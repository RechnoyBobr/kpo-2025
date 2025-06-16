package hse.bank.records;

import hse.bank.domains.BankAccount;
import hse.bank.domains.Category;
import java.util.Optional;

public record OperationData(boolean type, BankAccount account, double amount, Category category, Optional<String> desc) implements ObjectData {

}
