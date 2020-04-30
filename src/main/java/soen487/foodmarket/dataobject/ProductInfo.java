package soen487.foodmarket.dataobject;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Data
public class ProductInfo {

    @Id
    @Column(length = 32, nullable = false, unique = true)
    private String productId;

    @Column(length = 50, nullable = false)
    private String productName;

    @Column(nullable= false, columnDefinition = "DECIMAL(8,2)")
    private BigDecimal productPrice;

    @Column(nullable = false)
    private Integer productStock;

    private String productDescription;

    private String productImage;

    @Column(nullable= false, columnDefinition = "tinyint default 0")
    private Integer productStatus; // 0: 在售；1：下架

    @Column(nullable = false)
    private Integer category;

    @Column(nullable = false)
    private Integer productOwnerId;

    @Column(columnDefinition="TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP")
    private Date createTime;

    @Column(columnDefinition="TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP on update current_timestamp")
    private Date updateTime;
}
