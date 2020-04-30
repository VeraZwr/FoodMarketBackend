package soen487.foodmarket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soen487.foodmarket.dataobject.OrderItem;
import soen487.foodmarket.dataobject.OrderMaster;
import soen487.foodmarket.dataobject.ProductInfo;
import soen487.foodmarket.enums.OrderStatus;
import soen487.foodmarket.enums.PayStatus;
import soen487.foodmarket.error.BusinessException;
import soen487.foodmarket.error.EmBusinessError;
import soen487.foodmarket.models.Cart;
import soen487.foodmarket.models.OrderDTO;
import soen487.foodmarket.repository.OrderItemRepository;
import soen487.foodmarket.repository.OrderMasterRepository;
import soen487.foodmarket.repository.ProductRepository;
import soen487.foodmarket.utils.KeyUtil;
import soen487.foodmarket.viewobjects.OrderItemVO;
import soen487.foodmarket.viewobjects.OrderVO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderMasterRepository orderMasterRepository;

    private final OrderItemRepository orderItemRepository;

    private final ProductRepository productRepository;

    private final ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderMasterRepository orderMasterRepository,
                            OrderItemRepository orderItemRepository, ProductRepository productRepository, ProductService productService) {
        this.orderMasterRepository = orderMasterRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public OrderVO create(OrderDTO orderDTO) {
        String orderMasterId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        List<OrderItemVO> orderItemVOList = new ArrayList<>();
        for (OrderItem orderItem : orderDTO.getOrderItemList()) {
            ProductInfo productInfo = productRepository.findById(orderItem.getProductId()).orElse(null);
            if (productInfo == null) {
                log.error("[create order] product does not exist. productId={}", orderItem.getProductId());
                throw new BusinessException(EmBusinessError.PRODUCT_NOT_EXIST);
            }
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal((orderItem.getQuantity())))
                    .add(orderAmount);
            orderItem.setItemId(KeyUtil.genUniqueKey());
            orderItem.setOrderId(orderMasterId);
            OrderItem save = orderItemRepository.save(orderItem);
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(save, orderItemVO);
            BeanUtils.copyProperties(productInfo, orderItemVO);
            orderItemVOList.add(orderItemVO);
        }
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderMasterId);
        orderDTO.setOrderAmount(orderAmount);
        orderDTO.setOrderStatus(OrderStatus.NEW.getCode());
        orderDTO.setPayStatus(PayStatus.WAIT.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMasterRepository.save(orderMaster);

        List<Cart> cartList = orderDTO.getOrderItemList().stream().map(e ->
                new Cart(e.getProductId(), e.getQuantity())).collect(Collectors.toList());
        productService.decreaseStock(cartList);
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orderDTO, orderVO);
        orderVO.setOrderItemVOList(orderItemVOList);
        return orderVO;
    }

    @Override
    public List<OrderVO> findOrdersByBuyerId(Integer buyerId) {
        List<OrderMaster> orderMasterList = orderMasterRepository.findByBuyerId(buyerId);
        List<String> orderIdList = orderMasterList.stream().map(OrderMaster::getOrderId).collect(Collectors.toList());
        List<OrderItem> orderItemList = orderItemRepository.findAllByOrderIdIn(orderIdList);
        List<OrderVO> orderVOList = new ArrayList<>();
        for (OrderMaster orderMaster : orderMasterList) {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(orderMaster, orderVO);
            List<OrderItemVO> orderItemVOList = new ArrayList<>();
            for (OrderItem orderItem : orderItemList) {
                if (orderItem.getOrderId().equals(orderMaster.getOrderId())) {
                    OrderItemVO orderItemVO = new OrderItemVO();
                    BeanUtils.copyProperties(orderItem, orderItemVO);
                    productRepository.findById(orderItem.getProductId())
                            .ifPresent(productInfo -> BeanUtils.copyProperties(productInfo, orderItemVO));
                    orderItemVOList.add(orderItemVO);
                }
            }
            orderVO.setOrderItemVOList(orderItemVOList);
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    @Override
    public List<OrderVO> listByOwnerId(Integer ownerId) {
        List<ProductInfo> productInfoList = productRepository.findAllByProductOwnerId(ownerId);
        List<String> productIdList = productInfoList.stream().
                map(ProductInfo::getProductId).collect(Collectors.toList());
        List<OrderItem> orderItemList = orderItemRepository.findAllByProductIdIn(productIdList);
        List<String> orderIdList = orderItemList.stream().map(OrderItem::getOrderId).collect(Collectors.toList());
        List<OrderMaster> orderMasterList = orderMasterRepository.findAllByOrderIdIn(orderIdList);
        List<OrderVO> orderVOList = new ArrayList<>();
        for (OrderMaster orderMaster : orderMasterList) {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(orderMaster, orderVO);
            List<OrderItemVO> orderItemVOList = new ArrayList<>();
            for (OrderItem orderItem : orderItemList) {
                if (orderItem.getOrderId().equals(orderMaster.getOrderId())) {
                    OrderItemVO orderItemVO = new OrderItemVO();
                    BeanUtils.copyProperties(orderItem, orderItemVO);
                    productRepository.findById(orderItem.getProductId())
                            .ifPresent(productInfo -> BeanUtils.copyProperties(productInfo, orderItemVO));
                    orderItemVOList.add(orderItemVO);
                }
            }
            orderVO.setOrderItemVOList(orderItemVOList);
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    @Override
    @Transactional
    public OrderMaster cancelByBuyer(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if (orderMaster == null) {
            log.error("[cancel order] no such an order. orderId={}", orderId);
            throw new BusinessException(EmBusinessError.ORDER_NOT_EXIST);
        }
        if (!orderMaster.getOrderStatus().equals(OrderStatus.NEW.getCode())) {
            log.error("[cancel order]order cannot be cancelled with this status. orderId={}, orderStatus={}",
                    orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new BusinessException(EmBusinessError.ORDER_STATUS_ERROR);
        }
        orderMaster.setOrderStatus(OrderStatus.CANCEL.getCode());
        if (orderMaster.getPayStatus().equals(PayStatus.SUCCESS.getCode())) {
            // TODO refund
            orderMaster.setPayStatus(PayStatus.WAIT.getCode());
        }
        orderMasterRepository.save(orderMaster);
        // 返回库存
        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId);
        List<Cart> cartList = orderItemList.stream()
                .map(orderItem -> new Cart(orderItem.getProductId(), orderItem.getQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartList);
        return orderMaster;
    }

    @Override
    @Transactional
    public OrderMaster finish(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if (orderMaster == null) {
            log.error("[finish order] no such an order. orderId={}", orderId);
            throw new BusinessException(EmBusinessError.ORDER_NOT_EXIST);
        }
        if (!orderMaster.getOrderStatus().equals(OrderStatus.NEW.getCode())) {
            log.error("[finish order]order status error. orderId={}, orderStatus={}",
                    orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new BusinessException(EmBusinessError.ORDER_STATUS_ERROR);
        }
        if (!orderMaster.getPayStatus().equals(PayStatus.SUCCESS.getCode())) {
            log.error("[finish order]order has not been paid. orderId={}, payStatus={}",
                    orderMaster.getOrderId(), orderMaster.getPayStatus());
            throw new BusinessException(EmBusinessError.PAY_STATUS_ERROR);
        }
        orderMaster.setOrderStatus(OrderStatus.FINISH.getCode());
        orderMasterRepository.save(orderMaster);
        return orderMaster;
    }

    @Override
    @Transactional
    public OrderMaster pay(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if (orderMaster == null) {
            log.error("[check out] no such an order. orderId={}", orderId);
            throw new BusinessException(EmBusinessError.ORDER_NOT_EXIST);
        }
        if (!orderMaster.getOrderStatus().equals(OrderStatus.NEW.getCode())) {
            log.error("[check out]order status error. orderId={}, orderStatus={}",
                    orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new BusinessException(EmBusinessError.ORDER_STATUS_ERROR);
        }
        if (!orderMaster.getPayStatus().equals(PayStatus.WAIT.getCode())) {
            log.error("[check out]pay status error. orderId={}, payStatus={}",
                    orderMaster.getOrderId(), orderMaster.getPayStatus());
            throw new BusinessException(EmBusinessError.PAY_STATUS_ERROR);
        }
        orderMaster.setPayStatus(PayStatus.SUCCESS.getCode());
        return orderMasterRepository.save(orderMaster);
    }


    @Override
    public OrderDTO findByOrderId(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if (orderMaster == null) {
            log.error("[search order] no such an order. orderId={}", orderId);
            throw new BusinessException(EmBusinessError.ORDER_NOT_EXIST);
        }
        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId);
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderItemList(orderItemList);
        return orderDTO;
    }

}
