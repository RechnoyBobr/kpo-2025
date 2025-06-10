package hse.bank.stats;

import java.util.List;
import java.util.stream.Collectors;

public class StatsCollector {
    public static void printStats(List<Long> execTime) {
        long mean = execTime.stream().mapToLong(Long::longValue).sum() / execTime.size();
        System.out.println("Mean: " + mean);
        System.out.println("Max: " + execTime.stream().mapToLong(Long::longValue).max().getAsLong());
        System.out.println("Min: " + execTime.stream().mapToLong(Long::longValue).min().getAsLong());
        System.out.println("All executions: " + execTime.toString());

    }
}
