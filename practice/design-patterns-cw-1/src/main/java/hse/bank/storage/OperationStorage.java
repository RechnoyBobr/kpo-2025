package hse.bank.storage;


import hse.bank.domains.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
@RequiredArgsConstructor
public class OperationStorage {
    @Getter
    @Setter
    private static List<Operation> operations = new ArrayList<>();

    public static void addOperation(Operation op) {
        operations.add(op);
    }

    public static Operation getOperationById(int id) {
        return operations.stream().filter(op -> op.getId() == id).findFirst().orElse(null);
    }

    public static void deleteOperationById(int id) {
        operations.removeIf(op -> op.getId() == id);
    }
}
