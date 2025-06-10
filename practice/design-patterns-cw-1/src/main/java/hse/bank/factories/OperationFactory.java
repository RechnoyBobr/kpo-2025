package hse.bank.factories;

import hse.bank.domains.Operation;
import hse.bank.records.OperationData;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OperationFactory {
    private static int globalId = -1;

    public static Operation createOperation(OperationData data) {
        if (data.account().getBalance() + data.amount() < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        globalId++;
        double newBalance = data.account().getBalance() + data.amount();
        data.account().setBalance(newBalance);
        return new Operation(globalId, data.type(), data.account(), data.amount(), LocalDateTime.now(), data.category(), data.desc());
    }

    public static Operation createOperationWithId(OperationData data, int id) {
        if (id > globalId) {
            globalId = id;
        }
        return new Operation(id, data.type(), data.account(), data.amount(), LocalDateTime.now(), data.category(), data.desc());
    }
    public static Operation createOperationWithIdAndTime(OperationData data,LocalDateTime time,  int id) {
        if (id > globalId) {
            globalId = id;
        }
        return new Operation(id, data.type(), data.account(), data.amount(), time, data.category(), data.desc());
    }

    public static void flush() {
        globalId = -1;
    }

}
