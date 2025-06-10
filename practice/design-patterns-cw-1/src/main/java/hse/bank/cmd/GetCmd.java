package hse.bank.cmd;

import hse.bank.records.CommandData;
import hse.bank.storage.AccountStorage;
import hse.bank.storage.CategoryStorage;
import hse.bank.storage.OperationStorage;
import org.springframework.stereotype.Component;

/**
 * Get cmd.
 */
@Component
public class GetCmd implements Command {


    @Override
    public CmdResult execute(CommandData data) {
        if (data.miscData().id() == CommandData.MISC_DATA.id()) {
            return new CmdResult<>(CategoryStorage.getCategories(), null);
        }
        return switch (data.domainType()) {
            case ACCOUNT -> new CmdResult<>(AccountStorage.getUserById(data.miscData().id()), null);
            case OPERATION -> new CmdResult<>(OperationStorage.getOperationById(data.miscData().id()), null);
            case CATEGORY -> new CmdResult<>(CategoryStorage.getCategoryById(data.miscData().id()), null);
        };
    }
}
