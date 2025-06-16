package hse.repositories;

import hse.entities.PaymentTask;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTasksRepository extends JpaRepository<PaymentTask, Integer> {
    List<PaymentTask> findAllByIsFinishedFalse();
}
