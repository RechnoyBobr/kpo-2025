package hse.bank.decorator;

import hse.bank.cmd.CmdResult;
import hse.bank.cmd.Command;
import hse.bank.records.CommandData;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Decorator for cmd.
 */
@Getter
@AllArgsConstructor
@Component
public class CommandDecorator {
    /**
     * List of operation time to execute.
     */
    private final List<Long> operationTimes = new ArrayList<>();

    /**
     * Execute command.
     *
     * @param command Command
     * @param data    Data
     * @return Result
     */
    public CmdResult execute(Command command, CommandData data) {
        Instant start = Instant.now();
        var res = command.execute(data);
        Instant end = Instant.now();
        long execTime = end.toEpochMilli() - start.toEpochMilli();
        operationTimes.add(execTime);
        return res;
    }

    /**
     * Get stats.
     *
     * @return Success if completes
     */
    public CmdResult getStats() {
        try {
            System.out.println("Operation total: " + operationTimes.size());
            System.out.println(
                "Operation average: " + operationTimes.stream().mapToDouble(x -> x).average().getAsDouble() + "ms");
            System.out.println(
                "Operation min: " + operationTimes.stream().mapToDouble(x -> x).min().getAsDouble() + "ms");
            System.out.println(
                "Operation max: " + operationTimes.stream().mapToDouble(x -> x).max().getAsDouble() + "ms");
        } catch (Exception e) {
            return CmdResult.failure(e.getMessage());
        }
        return CmdResult.success();
    }
}
