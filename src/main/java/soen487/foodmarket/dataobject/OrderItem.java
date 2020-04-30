package soen487.foodmarket.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Data
public class OrderItem {

    @Id
    @Column(length = 32, nullable = false)
    private String itemId;

    @Column(length = 32, nullable = false)
    private String orderId;

    @Column(length = 32, nullable = false)
    private String productId;

    @Column(nullable = false)
    private Integer quantity;

    private String unit;

    @Column(columnDefinition="TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP")
    private Date createTime;

    @Column(columnDefinition="TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP on update current_timestamp")
    private Date updateTime;
}
