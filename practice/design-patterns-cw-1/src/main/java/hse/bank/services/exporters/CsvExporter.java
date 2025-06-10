package hse.bank.services.exporters;

import hse.bank.visitor.StorageVisitor;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Component;

/**
 * Class for exporting data to csv format.
 */
@Component
public class CsvExporter implements BaseExporter {

    @Override
    public void exportData(OutputStream outputStream) {
        try (PrintWriter writer = new PrintWriter(outputStream, true, StandardCharsets.UTF_8)) {

            writer.println("=== Accounts ===");
            writer.println("ID,Name,Balance");
            StorageVisitor.walkBankAccountStorage().forEach(account -> {
                writer.printf("%d,%s,%.2f%n", account.getId(), account.getName(), account.getBalance());
            });
            writer.println();

            writer.println("=== Categories ===");
            writer.println("ID,Name");
            StorageVisitor.walkCategoryStorage().forEach(category -> {
                writer.printf("%d,%b,%s%n", category.getId(), category.isPositive(), category.getName());
            });

            writer.println();

            writer.println("=== Operations ===");
            writer.println("ID,Amount,Type,CategoryID");
            StorageVisitor.walkOperationStorage().forEach(operation -> {
                writer.printf("%d,%b,%d,%.2f,%s,%d,%s%n",
                    operation.getId(),
                    operation.isType(),
                    operation.getAccount().getId(),
                    operation.getAmount(),
                    operation.getDate().toString(),
                    operation.getCategory().getId(),
                    operation.getDescription().isPresent() ? operation.getDescription().get() : ""
                );
            });
        }
    }
}
