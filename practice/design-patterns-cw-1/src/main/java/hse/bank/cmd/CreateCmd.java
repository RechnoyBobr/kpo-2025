package hse.bank.cmd;

import hse.bank.domains.*;
import hse.bank.factories.BankAccountFactory;
import hse.bank.factories.CategoryFactory;
import hse.bank.factories.OperationFactory;
import hse.bank.records.*;
import hse.bank.storage.AccountStorage;
import hse.bank.storage.CategoryStorage;
import hse.bank.storage.OperationStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateCmd implements Command {


    @Override
    public CmdResult execute(CommandData data) {
        return switch (data.domainType()) {
            case ACCOUNT -> {
                var accountData = (BankAccountData) data.objectData();
                var account = BankAccountFactory.createAccount(accountData);
                AccountStorage.addUser(account);
                yield new CmdResult<>(account, null);
            }
            case OPERATION -> {
                var operationData = (OperationData) data.objectData();
                var operation = OperationFactory.createOperation(operationData);
                OperationStorage.addOperation(operation);
                yield new CmdResult<>(operation, null);
            }
            case CATEGORY -> {
                var categoryData = (CategoryData) data.objectData();
                var category = CategoryFactory.createCategory(categoryData);
                CategoryStorage.addCategory(category);
                yield new CmdResult<>(category, null);
            }
        };
    }
}