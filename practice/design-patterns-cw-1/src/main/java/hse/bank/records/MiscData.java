package hse.bank.records;

import hse.bank.enums.IOFormat;

public record MiscData(int id, String filePath, IOFormat format) {
    public MiscData {
        if (filePath == null) {
            filePath = "";
        }
    }

    public MiscData(int id) {
        this(id, "", IOFormat.CSV);
    }

    public MiscData(String filePath, IOFormat format) {
        this(0, filePath, format);
    }
}
