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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

/**
 * Imports data from yaml file.
 */
@Log4j2
@Component
public class YamlImporter implements BaseImporter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Import data.
     */
    @Override
    public void importData(InputStream inputStream) throws IOException {
        try {
            // Создаем экземпляр Yaml
            Yaml yaml = new Yaml();

            Map<String, Object> data = yaml.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            importAccounts(data);
            importCategories(data);
            importOperations(data);

        } catch (Exception e) {
            log.error("Ошибка при импорте данных из YAML: {}", e.getMessage());
            throw new IOException("Ошибка при импорте данных из YAML", e);
        }
    }

    /**
     * Import accounts.
     *
     * @param data Data for import.
     */
    private void importAccounts(Map<String, Object> data) {
        if (!data.containsKey("accounts")) {
            return;
        }

        List<Map<String, Object>> accounts = (List<Map<String, Object>>) data.get("accounts");
        for (Map<String, Object> accountMap : accounts) {
            try {
                int id = ((Number) accountMap.get("id")).intValue();
                String name = (String) accountMap.get("name");
                double balance = ((Number) accountMap.get("balance")).doubleValue();

                BankAccountData accountData = new BankAccountData(name, balance);
                BankAccount account = BankAccountFactory.createAccountWithId(accountData, id);
                AccountStorage.addUser(account);
            } catch (Exception e) {
                log.warn("Ошибка при импорте счета: {}", e.getMessage());
            }
        }
    }

    /**
     * Import categories.
     *
     * @param data Data for import.
     */
    private void importCategories(Map<String, Object> data) {
        if (!data.containsKey("categories")) {
            return;
        }

        List<Map<String, Object>> categories = (List<Map<String, Object>>) data.get("categories");
        for (Map<String, Object> categoryMap : categories) {
            try {
                int id = ((Number) categoryMap.get("id")).intValue();
                String name = (String) categoryMap.get("name");
                boolean isPositive = (Boolean) categoryMap.get("positive");

                CategoryData categoryData = new CategoryData(isPositive, name);
                Category category = CategoryFactory.createCategoryWithId(categoryData, id);
                CategoryStorage.addCategory(category);
            } catch (Exception e) {
                log.warn("Ошибка при импорте категории: {}", e.getMessage());
            }
        }
    }

    /**
     * Import operations.
     *
     * @param data Data for import.
     */
    private void importOperations(Map<String, Object> data) {
        if (!data.containsKey("operations")) {
            return;
        }
        List<Map<String, Object>> operations = (List<Map<String, Object>>) data.get("operations");
        for (Map<String, Object> operationMap : operations) {
            try {
                int accountId = ((Number) operationMap.get("accountId")).intValue();
                BankAccount account = AccountStorage.getUserById(accountId);
                if (account == null) {
                    log.warn("Счет с ID {} не найден при импорте операции", accountId);
                    continue;
                }
                String dateStr = (String) operationMap.get("date");
                LocalDateTime date = LocalDateTime.parse(dateStr, DATE_FORMATTER);
                int categoryId = ((Number) operationMap.get("categoryId")).intValue();
                Category category = CategoryStorage.getCategoryById(categoryId);
                if (category == null) {
                    log.warn("Категория с ID {} не найдена при импорте операции", categoryId);
                    continue;
                }
                int id = ((Number) operationMap.get("id")).intValue();
                boolean type = (Boolean) operationMap.get("type");
                double amount = ((Number) operationMap.get("amount")).doubleValue();
                Optional<String> description = Optional.empty();
                if (operationMap.containsKey("description") && operationMap.get("description") != null) {
                    description = Optional.of((String) operationMap.get("description"));
                }
                OperationData operationData = new OperationData(
                    type,
                    account,
                    amount,
                    category,
                    description
                );

                Operation operation = OperationFactory.createOperationWithId(operationData, id);
                OperationStorage.addOperation(operation);
            } catch (Exception e) {
                log.warn("Ошибка при импорте операции: {}", e.getMessage());
            }
        }
    }
}
