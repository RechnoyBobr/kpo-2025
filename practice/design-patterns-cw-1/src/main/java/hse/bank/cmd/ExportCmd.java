package hse.bank.cmd;

import hse.bank.records.CommandData;
import hse.bank.services.exporters.CsvExporter;
import hse.bank.services.exporters.JsonExporter;
import hse.bank.services.exporters.YamlExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ExportCmd implements Command {
    private final CsvExporter csvExporter = new CsvExporter();
    private final YamlExporter yamlExporter = new YamlExporter();
    private final JsonExporter jsonExporter = new JsonExporter();

    @Override
    public CmdResult execute(CommandData data) {
        try (OutputStream out = new FileOutputStream(data.miscData().filePath())) {
            switch (data.miscData().format()) {
                case CSV -> csvExporter.exportData(out);
                case YAML -> yamlExporter.exportData(out);
                case JSON -> jsonExporter.exportData(out);
            }
        } catch (FileNotFoundException e) {
            return CmdResult.failure(e.getMessage());
        } catch (IOException e) {
            return CmdResult.failure(e.getMessage());
        }
        return CmdResult.success();
    }
}
