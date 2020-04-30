package soen487.foodmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soen487.foodmarket.dataobject.OrderItem;
import soen487.foodmarket.dataobject.OrderMaster;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

    List<OrderItem> findAllByOrderIdIn(List<String> orderIdList);

    List<OrderItem> findAllByProductIdIn(List<String> productIdList);

    List<OrderItem> findByOrderId(String orderId);
}
