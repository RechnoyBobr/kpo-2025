package hse.bank.services.importers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
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
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * Class for importing data from json file.
 */
@Log4j2
@Component
public class JsonImporter implements BaseImporter {

    /**
     * Imports data.
     *
     * @param inputStream Stream to import from
     * @throws IOException Exception if stream content is not a json document
     */
    @Override
    public void importData(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                    @Override
                    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                        throws JsonParseException {
                        return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    }
                })
                .create();

            JsonObject rootObject = JsonParser.parseReader(reader).getAsJsonObject();

            importAccounts(gson, rootObject);

            importCategories(gson, rootObject);

            importOperations(gson, rootObject);

        } catch (Exception e) {
            log.error("Ошибка при импорте данных из JSON: {}", e.getMessage());
            throw new IOException("Ошибка при импорте данных из JSON", e);
        }
    }

    /**
     * Import accounts.
     *
     * @param gson       Gson object
     * @param rootObject Root object
     */
    private void importAccounts(Gson gson, JsonObject rootObject) {
        if (!rootObject.has("accounts")) {
            return;
        }

        JsonArray accountsArray = rootObject.getAsJsonArray("accounts");
        for (JsonElement element : accountsArray) {
            JsonObject accountObject = element.getAsJsonObject();

            int id = accountObject.get("id").getAsInt();
            String name = accountObject.get("name").getAsString();
            double balance = accountObject.get("balance").getAsDouble();

            BankAccountData accountData = new BankAccountData(name, balance);
            BankAccount account = BankAccountFactory.createAccountWithId(accountData, id);
            AccountStorage.addUser(account);
        }
    }

    /**
     * Imports categories.
     *
     * @param gson       Gson object
     * @param rootObject Root object
     */
    private void importCategories(Gson gson, JsonObject rootObject) {
        if (!rootObject.has("categories")) {
            return;
        }

        JsonArray categoriesArray = rootObject.getAsJsonArray("categories");
        for (JsonElement element : categoriesArray) {
            JsonObject categoryObject = element.getAsJsonObject();

            int id = categoryObject.get("id").getAsInt();
            String name = categoryObject.get("name").getAsString();
            boolean isPositive = categoryObject.get("positive").getAsBoolean();

            CategoryData categoryData = new CategoryData(isPositive, name);
            Category category = CategoryFactory.createCategoryWithId(categoryData, id);
            CategoryStorage.addCategory(category);
        }
    }

    /**
     * Imports operations.
     *
     * @param gson       Gson object
     * @param rootObject Root object
     */
    private void importOperations(Gson gson, JsonObject rootObject) {
        if (!rootObject.has("operations")) {
            return;
        }

        JsonArray operationsArray = rootObject.getAsJsonArray("operations");
        for (JsonElement element : operationsArray) {
            JsonObject operationObject = element.getAsJsonObject();

            int accountId = operationObject.get("accountId").getAsInt();
            BankAccount account = AccountStorage.getUserById(accountId);
            if (account == null) {
                log.warn("Счет с ID {} не найден при импорте операции", accountId);
                continue;
            }

            LocalDateTime date = gson.fromJson(
                operationObject.get("date"),
                LocalDateTime.class
            );

            int categoryId = operationObject.get("categoryId").getAsInt();
            Category category = CategoryStorage.getCategoryById(categoryId);
            if (category == null) {
                log.warn("Категория с ID {} не найдена при импорте операции", categoryId);
                continue;
            }

            Optional<String> description = Optional.empty();
            if (operationObject.has("description") && !operationObject.get("description").isJsonNull()) {
                description = Optional.of(operationObject.get("description").getAsString());
            }
            int id = operationObject.get("id").getAsInt();
            boolean type = operationObject.get("type").getAsBoolean();
            double amount = operationObject.get("amount").getAsDouble();
            OperationData operationData = new OperationData(
                type,
                account,
                amount,
                category,
                description
            );

            Operation operation = OperationFactory.createOperationWithId(operationData, id);
            OperationStorage.addOperation(operation);
        }
    }
}
