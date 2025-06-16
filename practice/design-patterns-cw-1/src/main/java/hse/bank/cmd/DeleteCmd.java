package hse.bank.cmd;

import hse.bank.records.CommandData;
import hse.bank.storage.AccountStorage;
import hse.bank.storage.CategoryStorage;
import hse.bank.storage.OperationStorage;
import org.springframework.stereotype.Component;

/**
 * Delete command.
 */
@Component
public class DeleteCmd implements Command {
    @Override
    public CmdResult execute(CommandData data) {
        return switch (data.domainType()) {
            case ACCOUNT -> {
                AccountStorage.deleteUserById(data.miscData().id());
                yield new CmdResult<>(null, null);
            }
            case OPERATION -> {
                OperationStorage.deleteOperationById(data.miscData().id());
                yield new CmdResult<>(null, null);
            }
            case CATEGORY -> {
                CategoryStorage.deleteCategoryById(data.miscData().id());
                yield new CmdResult<>(null, null);
            }
        };
    }
} 