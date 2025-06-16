package hse.bank.services.exporters;

import hse.bank.visitor.StorageVisitor;

import java.io.OutputStream;

public interface BaseExporter {
   void exportData(OutputStream outputStream);
}
