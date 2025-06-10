package hse.bank.services.exporters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import hse.bank.domains.Operation;
import hse.bank.visitor.StorageVisitor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * Class for json export.
 */
@Log4j2
@Component
public class JsonExporter implements BaseExporter {

    @Override
    public void exportData(OutputStream outputStream) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("accounts", StorageVisitor.walkBankAccountStorage().collect(Collectors.toList()));
        data.put("categories", StorageVisitor.walkCategoryStorage().collect(Collectors.toList()));
        data.put("operations", StorageVisitor.walkOperationStorage().collect(Collectors.toList()));

        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                @Override
                public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                }
            })
            .registerTypeAdapter(Operation.class, new JsonSerializer<Operation>() {
                @Override
                public JsonElement serialize(Operation operation, Type typeOfSrc, JsonSerializationContext context) {
                    JsonObject jsonObject = new JsonObject();

                    jsonObject.addProperty("id", operation.getId());
                    jsonObject.addProperty("type", operation.isType());
                    jsonObject.addProperty("amount", operation.getAmount());

                    jsonObject.addProperty("accountId", operation.getAccount().getId());

                    jsonObject.add("date", context.serialize(operation.getDate()));

                    jsonObject.addProperty("categoryId", operation.getCategory().getId());

                    operation.getDescription().ifPresent(desc -> jsonObject.addProperty("description", desc));

                    return jsonObject;
                }
            })
            .setPrettyPrinting()
            .create();

        try {
            String result = gson.toJson(data);
            outputStream.write(result.getBytes());
        } catch (IOException e) {
            log.error("Ошибка при экспорте данных в JSON: {}", e.getMessage());
        }
    }
}
