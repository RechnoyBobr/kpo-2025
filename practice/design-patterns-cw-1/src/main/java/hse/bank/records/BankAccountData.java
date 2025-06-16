package hse.bank.records;

public record BankAccountData(String name, double initialBalance) implements ObjectData {
}
