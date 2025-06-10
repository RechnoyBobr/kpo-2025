package hse.bank.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hse.bank.enums.IoFormat;
import hse.bank.facades.BankFacade;
import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for the IO functionality of the bank system.
 */
@SpringBootTest
class IoTest {
    @Autowired
    private BankFacade facade;
    private final String[] values = {"./test.csv", "./test.json", "./test.yaml"};

    /**
     * Cleans up test files before each test.
     */
    @BeforeEach
    void setUp() {
        for (String value : values) {
            File f = new File(value);
            if (f.isFile()) {
                boolean isDeleted = f.delete();
                if (!isDeleted) {
                    // Log failed deletion or handle in a different way
                }
            }
        }
    }

    /**
     * Helper method to export data to CSV format.
     */
    void exportToCsv() {
        facade.flush();
        facade.createAccount("Test", 100.0);
        facade.createCategory("Test cat", true);
        facade.createOperation(20.0, 0, false, 0);
        facade.exportData("./test.csv", IoFormat.CSV);
        Assertions.assertDoesNotThrow(() -> {
            File f = new File("./test.csv");
            assertTrue(f.isFile());
        });
    }

    /**
     * Tests importing data from CSV format.
     */
    @Test
    void importToCsv() {
        exportToCsv();

        facade.flush();
        facade.importData("./test.csv", IoFormat.CSV);
        assertEquals(120.0, facade.getAccount(0).getBalance());
    }

    /**
     * Helper method to export data to JSON format.
     */
    void exportToJson() {
        facade.flush();
        facade.createAccount("Test", 100.0);
        facade.createCategory("Test cat", true);
        facade.createOperation(20.0, 0, false, 0);
        facade.exportData("./test.json", IoFormat.JSON);
        Assertions.assertDoesNotThrow(() -> {
            File f = new File("./test.json");
            assertTrue(f.isFile());
        });
    }

    /**
     * Tests importing data from JSON format.
     */
    @Test
    void importFromJson() {
        exportToJson();

        facade.flush();
        facade.importData("./test.json", IoFormat.JSON);
        assertEquals(120.0, facade.getAccount(0).getBalance());
    }

    /**
     * Helper method to export data to YAML format.
     */
    void exportToYaml() {
        facade.flush();
        facade.createAccount("Test", 100.0);
        facade.createCategory("Test cat", true);
        facade.createOperation(20.0, 0, false, 0);
        facade.exportData("./test.yaml", IoFormat.YAML);
        Assertions.assertDoesNotThrow(() -> {
            File f = new File("./test.yaml");
            assertTrue(f.isFile());
        });
        facade.flush();
    }

    /**
     * Tests importing data from YAML format.
     */
    @Test
    void importFromYaml() {
        exportToYaml();
        facade.flush();
        facade.importData("./test.yaml", IoFormat.YAML);
        assertEquals(120.0, facade.getAccount(0).getBalance());
    }
}
