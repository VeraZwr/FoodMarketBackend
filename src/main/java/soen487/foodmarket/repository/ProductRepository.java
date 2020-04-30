package soen487.foodmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soen487.foodmarket.dataobject.ProductInfo;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);

    List<ProductInfo> findAllByProductOwnerId(Integer ownerId);
}
