package hse.repositories;

import hse.entities.OrderEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    OrderEntity findFirstById(Integer integer);

    List<OrderEntity> getAllByUserId(Integer userId);
}
