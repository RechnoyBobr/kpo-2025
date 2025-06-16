package hse.services;

import hse.dto.Order;
import hse.entities.OrderEntity;
import hse.entities.OrderEventEntity;
import hse.repositories.OrderRepository;
import hse.repositories.OrderTaskRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    /**
     * Order repository
     */
    private final OrderRepository orderRepository;

    /**
     * Order task repository.
     */
    private final OrderTaskRepository orderTaskRepository;


    public void createOrder(Order order) {
        System.out.println("Got an order:" + order);
        OrderEntity entity = new OrderEntity();
        entity.setAmount(order.amount());
        entity.setUserId(order.user_id());
        entity.setDescription(order.description());
        entity.setStatus("NEW");
        orderRepository.save(entity);
        OrderEventEntity orderEventEntity = new OrderEventEntity();
        orderEventEntity.setId(entity.getId());
        orderEventEntity.setUserId(order.user_id());
        orderEventEntity.setAmount(order.amount());
        orderEventEntity.setIsFinished(false);
        orderTaskRepository.save(orderEventEntity);
    }

    public String getOrders(Integer userId) {
        List<OrderEntity> orderEntities = orderRepository.getAllByUserId(userId);
        List<Order> orders = orderEntities.stream().map(Order::fromEntity).toList();
        return orders.stream().map(Order::format).collect(Collectors.joining("\n\n"));
    }

    public String getOrder(Integer orderId) {
        OrderEntity orderEntity = orderRepository.getReferenceById(orderId);
        return String.format(
                "Order ID: %d, Amount %f, Status: %s",
                orderEntity.getId(),
                orderEntity.getAmount(),
                orderEntity.getStatus()
        );
    }
}
