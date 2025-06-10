package hse.bank.records;

import hse.bank.enums.CmdType;
import hse.bank.enums.DomainObjectType;
import hse.bank.enums.IOFormat;

public record CommandData(CmdType type, DomainObjectType domainType, ObjectData objectData, MiscData miscData) {
    public static final MiscData MISC_DATA = new MiscData(-1, "", IOFormat.CSV);
    public static final ObjectData OBJECT_DATA = new BankAccountData("", -1);

    public CommandData(CmdType type, DomainObjectType domainType, ObjectData objectData) {
        this(type, domainType, objectData, MISC_DATA);
    }

    public CommandData(CmdType type, DomainObjectType domainType, MiscData miscData) {
        this(type, domainType, OBJECT_DATA, miscData);
    }
}
