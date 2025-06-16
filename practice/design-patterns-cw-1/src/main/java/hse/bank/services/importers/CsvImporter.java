package hse.bank.services.importers;

import hse.bank.domains.BankAccount;
import hse.bank.domains.Category;
import hse.bank.domains.Operation;
import hse.bank.factories.BankAccountFactory;
import hse.bank.factories.CategoryFactory;
import hse.bank.factories.OperationFactory;
import hse.bank.records.BankAccountData;
import hse.bank.records.CategoryData;
import hse.bank.records.OperationData;
import hse.bank.storage.AccountStorage;
import hse.bank.storage.CategoryStorage;
import hse.bank.storage.OperationStorage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Class for importing data from csv file.
 */
@Component
public class CsvImporter implements BaseImporter {
    /**
     * Imports data.
     *
     * @param inputStream Stream to import from
     * @throws IOException Exception if stream content is not a csv table
     */
    @Override
    public void importData(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            String currentSection = null;
            List<String> headers = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (line.startsWith("===")) {
                    currentSection = line.replace("===", "").trim();
                    headers = null;
                    continue;
                }

                if (headers == null) {
                    headers = List.of(line.split(","));
                    continue;
                }

                String[] values = line.split(",");
                switch (currentSection) {
                    case "Accounts" -> importAccount(values);
                    case "Categories" -> importCategory(values);
                    case "Operations" -> importOperation(values);
                    default -> throw new IllegalArgumentException("Unknown section: " + currentSection);
                }
            }
        }
    }

    /**
     * Import accounts.
     *
     * @param values Values to import from
     */
    private void importAccount(String[] values) {
        int id = Integer.parseInt(values[0]);
        String name = values[1];
        double balance = Double.parseDouble(values[2]);

        BankAccountData accountData = new BankAccountData(name, balance);
        BankAccount account = BankAccountFactory.createAccountWithId(accountData, id);
        AccountStorage.addUser(account);
    }

    /**
     * Import categories.
     *
     * @param values Values to import from
     */
    private void importCategory(String[] values) {
        int id = Integer.parseInt(values[0]);
        boolean type = Boolean.parseBoolean(values[1]);
        String name = values[2];
        CategoryData categoryData = new CategoryData(true, name);
        Category category = CategoryFactory.createCategoryWithId(categoryData, id);
        CategoryStorage.addCategory(category);
    }

    /**
     * Import operations.
     *
     * @param values Values to import from
     */
    private void importOperation(String[] values) {
        int id = Integer.parseInt(values[0]);
        boolean type = Boolean.parseBoolean(values[1]);
        int accountId = Integer.parseInt(values[2]);
        BankAccount bankAccount = AccountStorage.getUserById(accountId);
        double amount = Double.parseDouble(values[3]);
        LocalDateTime time = LocalDateTime.parse(values[4]);
        int categoryId = Integer.parseInt(values[5]);
        Category category = CategoryStorage.getCategoryById(categoryId);
        Optional<String> desc = Optional.empty();
        if (values.length > 6) {
            desc = Optional.of(values[6]);
        }
        OperationData data = new OperationData(type, bankAccount, amount, category, desc);
        Operation result = OperationFactory.createOperationWithIdAndTime(data, time, id);
        OperationStorage.addOperation(result);
    }
}
