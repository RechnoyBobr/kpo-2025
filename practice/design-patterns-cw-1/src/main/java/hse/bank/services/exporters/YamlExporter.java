package hse.bank.services.exporters;

import hse.bank.domains.BankAccount;
import hse.bank.domains.Category;
import hse.bank.domains.Operation;
import hse.bank.visitor.StorageVisitor;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 * Class to export data to YAML format.
 */
@Log4j2
@Component
public class YamlExporter implements BaseExporter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Export data to yaml format.
     *
     * @param outputStream Output stream to export data.
     */
    @Override
    public void exportData(OutputStream outputStream) {
        try {
            Map<String, Object> data = new LinkedHashMap<>();

            List<Map<String, Object>> accountsMap = StorageVisitor.walkBankAccountStorage()
                .map(this::convertBankAccountToMap)
                .collect(Collectors.toList());
            data.put("accounts", accountsMap);

            List<Map<String, Object>> categoriesMap = StorageVisitor.walkCategoryStorage()
                .map(this::convertCategoryToMap)
                .collect(Collectors.toList());
            data.put("categories", categoriesMap);

            List<Map<String, Object>> operationsMap = StorageVisitor.walkOperationStorage()
                .map(this::convertOperationToMap)
                .collect(Collectors.toList());
            data.put("operations", operationsMap);

            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);

            Yaml yaml = new Yaml(options);

            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
                yaml.dump(data, writer);
            }

        } catch (IOException e) {
            log.error("Ошибка при экспорте данных в YAML: {}", e.getMessage());
        }
    }

    /**
     * Converts BankAccount object to map.
     */
    private Map<String, Object> convertBankAccountToMap(BankAccount account) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", account.getId());
        map.put("name", account.getName());
        map.put("balance", account.getBalance());
        return map;
    }

    /**
     * Converts Category object to map.
     */
    private Map<String, Object> convertCategoryToMap(Category category) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", category.getId());
        map.put("name", category.getName());
        map.put("positive", category.isPositive());
        return map;
    }

    /**
     * Converts Operation object to map.
     */
    private Map<String, Object> convertOperationToMap(Operation operation) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", operation.getId());
        map.put("type", operation.isType());
        map.put("amount", operation.getAmount());
        map.put("accountId", operation.getAccount().getId());
        map.put("date", operation.getDate().format(DATE_FORMATTER));
        map.put("categoryId", operation.getCategory().getId());
        operation.getDescription().ifPresent(desc -> map.put("description", desc));
        return map;
    }
}
