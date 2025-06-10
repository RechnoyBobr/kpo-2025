package hse.bank.decorator;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hse.bank.cmd.CmdResult;
import hse.bank.cmd.Command;
import hse.bank.domains.BankAccount;
import hse.bank.domains.Category;
import hse.bank.domains.Operation;
import hse.bank.enums.CmdType;
import hse.bank.enums.DomainObjectType;
import hse.bank.records.BankAccountData;
import hse.bank.records.CategoryData;
import hse.bank.records.CommandData;
import hse.bank.records.MiscData;
import hse.bank.records.OperationData;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


/**
 * Tests for the CommandDecorator class.
 */
@ExtendWith(MockitoExtension.class)
class CommandDecoratorTest {

    @Mock
    private Command command;

    @InjectMocks
    private CommandDecorator commandDecorator;

    private BankAccount testAccount;
    private Category testCategory;
    private Operation testOperation;
    private CmdResult<BankAccount> accountResult;
    private CmdResult<Category> categoryResult;
    private CmdResult<Operation> operationResult;

    @BeforeEach
    void setUp() {
        testAccount = new BankAccount(0, "Test Account", 1000.0);
        testCategory = new Category(0, true, "Test Category");
        testOperation = new Operation(
            0, true, testAccount, 100.0, LocalDateTime.now(),
            testCategory, Optional.empty()
        );

        accountResult = new CmdResult<>(testAccount, null);
        categoryResult = new CmdResult<>(testCategory, null);
        operationResult = new CmdResult<>(testOperation, null);
    }

    @Test
    void execute_ShouldExecuteCommandAndLog() {
        CommandData commandData = new CommandData(
            CmdType.CREATE,
            DomainObjectType.ACCOUNT,
            new BankAccountData("Test Account", 1000.0),
            CommandData.MISC_DATA
        );
        when(command.execute(any())).thenReturn(accountResult);

        CmdResult result = commandDecorator.execute(command, commandData);

        assertNotNull(result);
        assertEquals(testAccount, result.getBankAccount());
        verify(command).execute(commandData);
    }

    @Test
    void execute_ShouldHandleNullResult() {
        CommandData commandData = new CommandData(
            CmdType.GET,
            DomainObjectType.ACCOUNT,
            CommandData.OBJECT_DATA,
            new MiscData(1)
        );
        when(command.execute(any())).thenReturn(null);

        CmdResult result = commandDecorator.execute(command, commandData);

        assertNull(result);
        verify(command).execute(commandData);
    }

    @Test
    void execute_ShouldHandleException() {
        CommandData commandData = new CommandData(
            CmdType.CREATE,
            DomainObjectType.ACCOUNT,
            new BankAccountData("Test Account", 1000.0),
            CommandData.MISC_DATA
        );
        when(command.execute(any())).thenThrow(new RuntimeException("Test exception"));

        assertThrows(RuntimeException.class, () -> commandDecorator.execute(command, commandData));
        verify(command).execute(commandData);
    }

    @Test
    void execute_ShouldHandleDifferentDomainTypes() {
        CommandData accountData = new CommandData(
            CmdType.CREATE,
            DomainObjectType.ACCOUNT,
            new BankAccountData("Test Account", 1000.0),
            CommandData.MISC_DATA
        );
        CommandData operationData = new CommandData(
            CmdType.CREATE,
            DomainObjectType.OPERATION,
            new OperationData(true, testAccount, 100.0, testCategory, Optional.empty()),
            CommandData.MISC_DATA
        );
        CommandData categoryData = new CommandData(
            CmdType.CREATE,
            DomainObjectType.CATEGORY,
            new CategoryData(true, "Test Category"),
            CommandData.MISC_DATA
        );

        when(command.execute(any())).thenReturn(accountResult)
            .thenReturn(operationResult)
            .thenReturn(categoryResult);

        CmdResult accountResult = commandDecorator.execute(command, accountData);
        CmdResult operationResult = commandDecorator.execute(command, operationData);
        CmdResult categoryResult = commandDecorator.execute(command, categoryData);

        assertNotNull(accountResult);
        assertNotNull(operationResult);
        assertNotNull(categoryResult);
        assertEquals(testAccount, accountResult.getBankAccount());
        assertEquals(testOperation, operationResult.getOperation());
        assertEquals(testCategory, categoryResult.getCategory());
        verify(command, times(3)).execute(any());
    }
} 