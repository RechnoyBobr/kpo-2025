package hse.repositories;

import hse.entities.OrderEventEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTaskRepository extends JpaRepository<OrderEventEntity, Integer> {
    List<OrderEventEntity> findAllByIsFinishedFalse();
}
