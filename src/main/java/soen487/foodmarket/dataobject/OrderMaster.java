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
public class OrderMaster {

    @Id
    @Column(length = 32, nullable = false)
    private String orderId;

    @Column(nullable = false)
    private Integer buyerId;

    @Column(length = 100, nullable = false)
    private String buyerName;

    @Column(length = 20, nullable = false)
    private String buyerPhone;

    @Column(nullable = false)
    private String buyerAddress;

    @Column(nullable= false, columnDefinition = "DECIMAL(8,2)")
    private BigDecimal orderAmount;

    @Column(nullable= false, columnDefinition = "tinyint default 0")
    private Integer orderStatus; // 0: 新订单； 1：完结； 2： 已取消

    @Column(nullable= false, columnDefinition = "tinyint default 0")
    private Integer payStatus; // 0: 未支付；1：已支付

    @Column(columnDefinition="TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP")
    private Date createTime;

    @Column(columnDefinition="TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP on update current_timestamp")
    private Date updateTime;
}
