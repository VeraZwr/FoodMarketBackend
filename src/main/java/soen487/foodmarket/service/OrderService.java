package soen487.foodmarket.service;

import soen487.foodmarket.dataobject.OrderMaster;
import soen487.foodmarket.models.OrderDTO;
import soen487.foodmarket.viewobjects.OrderVO;

import java.util.List;

public interface OrderService {

    OrderVO create(OrderDTO orderDTO);

    List<OrderVO> findOrdersByBuyerId(Integer buyerId);

    OrderMaster cancelByBuyer(String orderId);

    OrderMaster finish(String orderId);

    OrderMaster pay(String orderId);

    OrderDTO findByOrderId(String orderId);

    List<OrderVO> listByOwnerId(Integer ownerId);

}
