package hse.bank.services.importers;

import hse.bank.visitor.StorageVisitor;

import java.io.IOException;
import java.io.InputStream;

public interface BaseImporter {
    void importData(InputStream inputStream)  throws IOException;
}
