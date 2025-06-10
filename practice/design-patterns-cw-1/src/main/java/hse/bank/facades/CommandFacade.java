package hse.bank.facades;

import hse.bank.cmd.AnalyticsCmd;
import hse.bank.cmd.CmdResult;
import hse.bank.cmd.CreateCmd;
import hse.bank.cmd.DeleteCmd;
import hse.bank.cmd.ExportCmd;
import hse.bank.cmd.GetCmd;
import hse.bank.cmd.ImportCmd;
import hse.bank.decorator.CommandDecorator;
import hse.bank.enums.DomainObjectType;
import hse.bank.records.BankAccountData;
import hse.bank.records.CategoryData;
import hse.bank.records.CommandData;
import hse.bank.records.OperationData;
import java.io.File;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A facade that provides a unified interface for executing various commands in the banking system.
 * Handles command routing and validation based on command data.
 */
@Log4j2
@Component
public class CommandFacade {
    /**
     * Command decorator.
     */
    @Autowired
    private CommandDecorator commandDecorator;
    /**
     * Create cmd.
     */
    @Autowired
    private CreateCmd createDomain;
    /**
     * Get cmd.
     */
    @Autowired
    private GetCmd getDomain;
    /**
     * Delete cmd.
     */
    @Autowired
    private DeleteCmd deleteDomain;
    /**
     * Export cmd.
     */
    @Autowired
    private ExportCmd exportCmd;
    /**
     * Import cmd.
     */
    @Autowired
    private ImportCmd importCmd;
    /**
     * Analytics cmd.
     */
    @Autowired
    private AnalyticsCmd analyticsCmd;

    /**
     * Executes a command based on the provided command data.
     * Routes the command to the appropriate handler based on the command type.
     *
     * @param data The data containing information about the command to execute
     * @return The result of the command execution
     * @throws IllegalStateException If the command data is invalid
     */
    public CmdResult execute(CommandData data) {
        try {
            validateData(data);
        } catch (IllegalStateException e) {
            log.error(e.getMessage());
            return CmdResult.failure(e.getMessage());
        }
        var res = switch (data.type()) {
            case CREATE -> commandDecorator.execute(createDomain, data);
            case GET -> commandDecorator.execute(getDomain, data);
            case DELETE -> commandDecorator.execute(deleteDomain, data);
            case STATISTICS -> commandDecorator.getStats();
            case EXPORT -> commandDecorator.execute(exportCmd, data);
            case IMPORT -> commandDecorator.execute(importCmd, data);
            case ANALYTICS -> commandDecorator.execute(analyticsCmd, data);
        };
        if (!res.isSuccess()) {
            log.error(res.getError());
        }
        return res;
    }

    /**
     * Validates that the command data is properly formatted and contains all required information.
     *
     * @param data The command data to validate
     * @throws IllegalStateException If the command data is invalid
     */
    private void validateData(CommandData data) throws IllegalStateException {
        if (data == null) {
            throw new IllegalStateException("Command data cannot be null");
        }

        var cmdType = data.type();
        var domainType = data.domainType();

        if (cmdType == null) {
            throw new IllegalStateException("Command type cannot be null");
        }

        if (domainType == null) {
            throw new IllegalStateException("Domain object type cannot be null");
        }

        switch (cmdType) {
            case CREATE -> validateCreateCommand(data);
            case IMPORT -> validateImportCommand(data);
            default -> {
            }
        }
    }

    /**
     * Validates a create command has the necessary object data.
     *
     * @param data The command data to validate
     * @throws IllegalStateException If the command data is invalid for a create operation
     */
    private void validateCreateCommand(CommandData data) {
        if (data.objectData() == null) {
            throw new IllegalStateException("Object data cannot be null for CREATE command");
        }

        if (
            data.domainType() == DomainObjectType.ACCOUNT
                && !(data.objectData() instanceof BankAccountData)
        ) {
            throw new IllegalStateException("You're trying to issue bank account with data for operation or category");
        }
        if (
            data.domainType() == DomainObjectType.OPERATION
                && !(data.objectData() instanceof OperationData)
        ) {
            throw new IllegalStateException("You're trying to make transaction with data for bank account or category");
        }
        if (
            data.domainType() == DomainObjectType.CATEGORY
                && !(data.objectData() instanceof CategoryData)
        ) {
            throw new IllegalStateException("You're trying to create category with data for operation or bank account");
        }
    }

    /**
     * Validates an import command has the necessary file path.
     *
     * @param data The command data to validate
     * @throws IllegalStateException If the command data is invalid for an import operation
     */
    private void validateImportCommand(CommandData data) {
        if (data.miscData() == null || data.miscData().filePath() == null) {
            throw new IllegalStateException("File path cannot be null for IMPORT command");
        }

        File f = new File(data.miscData().filePath());
        if (!f.isFile()) {
            throw new IllegalStateException("No such file exists");
        }
    }
}
