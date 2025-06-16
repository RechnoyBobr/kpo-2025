package hse.bank.facades;

import hse.bank.domains.BankAccount;
import hse.bank.domains.Category;
import hse.bank.domains.Operation;
import hse.bank.enums.CmdType;
import hse.bank.enums.DomainObjectType;
import hse.bank.enums.IoFormat;
import hse.bank.factories.BankAccountFactory;
import hse.bank.factories.CategoryFactory;
import hse.bank.factories.OperationFactory;
import hse.bank.records.BankAccountData;
import hse.bank.records.CategoryData;
import hse.bank.records.CommandData;
import hse.bank.records.MiscData;
import hse.bank.records.OperationData;
import hse.bank.storage.AccountStorage;
import hse.bank.storage.CategoryStorage;
import hse.bank.storage.OperationStorage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Main facade of application.
 */
@Component
public class BankFacade {
    /**
     * Command facade.
     */
    @Autowired
    private CommandFacade commandFacade;

    /**
     * Create account.
     *
     * @param name           Name
     * @param initialBalance Balance
     * @return Account
     */
    public BankAccount createAccount(String name, double initialBalance) {
        var commandData = new CommandData(
            CmdType.CREATE,
            DomainObjectType.ACCOUNT,
            new BankAccountData(name, initialBalance),
            CommandData.MISC_DATA
        );
        var result = commandFacade.execute(commandData);
        return result.getBankAccount();
    }

    /**
     * Create account.
     *
     * @param data Data
     */
    public BankAccount createAccount(CommandData data) {

        var result = commandFacade.execute(data);
        return result.getBankAccount();
    }

    /**
     * Get all categories.
     *
     * @return Categories
     */
    public List<Category> getCategories() {
        var result =
            commandFacade.execute(new CommandData(CmdType.GET, DomainObjectType.CATEGORY, CommandData.MISC_DATA));
        return result.getCategoriesList();
    }

    /**
     * Get account.
     *
     * @param id Id
     */
    public BankAccount getAccount(int id) {
        var commandData = new CommandData(
            CmdType.GET,
            DomainObjectType.ACCOUNT,
            CommandData.OBJECT_DATA,
            new MiscData(id)
        );
        var result = commandFacade.execute(commandData);
        return result.getBankAccount();
    }

    /**
     * Delete account.
     *
     * @param id Id
     */
    public void deleteAccount(int id) {
        var commandData = new CommandData(
            CmdType.DELETE,
            DomainObjectType.ACCOUNT,
            CommandData.OBJECT_DATA,
            new MiscData(id)
        );
        commandFacade.execute(commandData);
    }

    /**
     * Create operation.
     *
     * @param amount     Amount
     * @param accountId  To account
     * @param type       Type
     * @param categoryId With category
     * @return Operation
     */
    public Operation createOperation(double amount, int accountId, boolean type, int categoryId) {
        BankAccount bankAccount = getAccount(accountId);
        Category category = getCategory(categoryId);
        var commandData = new CommandData(
            CmdType.CREATE,
            DomainObjectType.OPERATION,
            new OperationData(type, bankAccount, amount, category, Optional.empty()),
            CommandData.MISC_DATA
        );
        var result = commandFacade.execute(commandData);
        return result.getOperation();
    }

    /**
     * Get operation.
     *
     * @param id Id
     */
    public Operation getOperation(int id) {
        var commandData = new CommandData(
            CmdType.GET,
            DomainObjectType.OPERATION,
            CommandData.OBJECT_DATA,
            new MiscData(id)
        );
        var result = commandFacade.execute(commandData);
        return result.getOperation();
    }

    /**
     * Delete operation.
     *
     * @param id Id
     */
    public void deleteOperation(int id) {
        var commandData = new CommandData(
            CmdType.DELETE,
            DomainObjectType.OPERATION,
            CommandData.OBJECT_DATA,
            new MiscData(id)
        );
        commandFacade.execute(commandData);
    }

    /**
     * Create category.
     *
     * @param name Name
     * @param type Type
     * @return Category
     */
    public Category createCategory(String name, boolean type) {
        var commandData = new CommandData(
            CmdType.CREATE,
            DomainObjectType.CATEGORY,
            new CategoryData(type, name),
            CommandData.MISC_DATA
        );
        var result = commandFacade.execute(commandData);
        return result.getCategory();
    }

    /**
     * Get category.
     *
     * @param id Id
     */
    public Category getCategory(int id) {
        var commandData = new CommandData(
            CmdType.GET,
            DomainObjectType.CATEGORY,
            CommandData.OBJECT_DATA,
            new MiscData(id)
        );
        var result = commandFacade.execute(commandData);
        return result.getCategory();
    }

    /**
     * Delete category.
     *
     * @param id Id
     */
    public void deleteCategory(int id) {
        var commandData = new CommandData(
            CmdType.DELETE,
            DomainObjectType.CATEGORY,
            CommandData.OBJECT_DATA,
            new MiscData(id)
        );
        commandFacade.execute(commandData);
    }

    /**
     * Transfer money.
     *
     * @param fromAccountId From
     * @param toAccountId   To
     * @param categoryId    Category id
     * @param amount        Amount
     */
    public void transferMoney(int fromAccountId, int toAccountId, int categoryId, double amount) {
        createOperation(-amount, fromAccountId, false, categoryId);
        createOperation(amount, toAccountId, true, categoryId);
    }

    /**
     * Deposits money.
     *
     * @param accountId  Account id
     * @param categoryId CategoryId
     * @param amount     Amount to deposit
     */
    public void depositMoney(int accountId, int categoryId, double amount) {
        createOperation(amount, accountId, true, categoryId);
    }

    /**
     * Withdraw money.
     *
     * @param accountId  Account id
     * @param categoryId CategoryId
     * @param amount     Amount to withdraw
     */
    public void withdrawMoney(int accountId, int categoryId, double amount) {
        createOperation(-amount, accountId, false, categoryId);
    }

    /**
     * Creates statistics.
     */
    public void generateStatistics() {
        var commandData = new CommandData(
            CmdType.STATISTICS,
            DomainObjectType.ACCOUNT,
            CommandData.OBJECT_DATA,
            CommandData.MISC_DATA
        );
        commandFacade.execute(commandData);
    }

    /**
     * Exports data.
     *
     * @param filePath File path
     * @param format   Format
     */
    public void exportData(String filePath, IoFormat format) {
        var commandData = new CommandData(
            CmdType.EXPORT,
            DomainObjectType.ACCOUNT,
            CommandData.OBJECT_DATA,
            new MiscData(filePath, format)
        );
        commandFacade.execute(commandData);
    }

    /**
     * Imports data.
     *
     * @param filePath File path.
     * @param format   Format.
     */
    public void importData(String filePath, IoFormat format) {
        var commandData = new CommandData(
            CmdType.IMPORT,
            DomainObjectType.ACCOUNT,
            CommandData.OBJECT_DATA,
            new MiscData(filePath, format)
        );
        commandFacade.execute(commandData);
    }

    /**
     * Deletes all data.
     */
    public void flush() {
        AccountStorage.setAccounts(new ArrayList<>());
        OperationStorage.setOperations(new ArrayList<>());
        CategoryStorage.setCategories(new ArrayList<>());
        BankAccountFactory.flush();
        CategoryFactory.flush();
        OperationFactory.flush();
    }
}
