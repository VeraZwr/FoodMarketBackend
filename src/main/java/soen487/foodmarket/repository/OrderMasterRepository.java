package soen487.foodmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soen487.foodmarket.dataobject.OrderMaster;

import java.util.List;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    List<OrderMaster> findByBuyerId(Integer buyerId);

    List<OrderMaster> findAllByOrderIdIn(List<String> orderIdList);

}
