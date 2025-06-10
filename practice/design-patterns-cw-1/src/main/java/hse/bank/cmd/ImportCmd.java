package hse.bank.cmd;

import hse.bank.records.CommandData;
import hse.bank.services.importers.CsvImporter;
import hse.bank.services.importers.JsonImporter;
import hse.bank.services.importers.YamlImporter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.stereotype.Component;

/**
 * Import cmd.
 */
@Component
public class ImportCmd implements Command {
    /**
     * Importer for csv.
     */
    private final CsvImporter csvImporter = new CsvImporter();
    /**
     * Importer for yaml.
     */
    private final YamlImporter yamlImporter = new YamlImporter();
    /**
     * Importer for json.
     */
    private final JsonImporter jsonImporter = new JsonImporter();

    @Override
    public CmdResult execute(CommandData data) {
        try (InputStream in = new FileInputStream(data.miscData().filePath())) {
            switch (data.miscData().format()) {
                case CSV -> csvImporter.importData(in);
                case YAML -> yamlImporter.importData(in);
                case JSON -> jsonImporter.importData(in);
                default -> {
                }
            }
        } catch (FileNotFoundException e) {
            return CmdResult.failure(e.getMessage());
        } catch (IOException e) {
            return CmdResult.failure(e.getMessage());
        }
        return CmdResult.success();
    }
}

