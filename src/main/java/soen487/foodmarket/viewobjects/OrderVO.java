package soen487.foodmarket.viewobjects;

import lombok.Data;
import soen487.foodmarket.dataobject.OrderItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderVO {

    private String orderId;

    private Integer buyerId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private BigDecimal orderAmount;

    private Integer orderStatus;

    private Integer payStatus;

    private Date createTime;

    private Date updateTime;

    private List<OrderItemVO> orderItemVOList;
}
