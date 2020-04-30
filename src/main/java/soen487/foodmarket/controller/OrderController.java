package soen487.foodmarket.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import soen487.foodmarket.dataobject.OrderItem;
import soen487.foodmarket.dataobject.OrderMaster;
import soen487.foodmarket.error.BusinessException;
import soen487.foodmarket.error.EmBusinessError;
import soen487.foodmarket.forms.OrderForm;
import soen487.foodmarket.models.CommonReturnType;
import soen487.foodmarket.models.OrderDTO;
import soen487.foodmarket.service.OrderService;
import soen487.foodmarket.utils.ParamValidator;
import soen487.foodmarket.viewobjects.OrderVO;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public CommonReturnType create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ParamValidator.paramError(bindingResult);
        }
        OrderDTO orderDTO = orderForm2OrderDTO(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderItemList())) {
            log.error("[create order] cart cannot be empty.");
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        OrderVO orderVO = orderService.create(orderDTO);
        return CommonReturnType.create(orderVO);
    }

    @GetMapping("/listByBuyer")
    public CommonReturnType listByBuyer(@RequestParam Integer buyerId) {
        List<OrderVO> orderVOList = orderService.findOrdersByBuyerId(buyerId);
        return CommonReturnType.create(orderVOList);
    }

    @GetMapping("/listByOwner")
    public CommonReturnType listByOwnerId(@RequestParam Integer ownerId) {
        List<OrderVO> orderVOList = orderService.listByOwnerId(ownerId);
        return CommonReturnType.create(orderVOList);
    }

    @PutMapping("/pay")
    public CommonReturnType pay(@RequestParam String orderId) {
        OrderMaster orderMaster = orderService.pay(orderId);
        return CommonReturnType.create(orderMaster);
    }

    @GetMapping("/findByOrderId")
    public CommonReturnType findByOrderId(@RequestParam String orderId) {
        OrderDTO orderDTO = orderService.findByOrderId(orderId);
        return CommonReturnType.create(orderDTO);
    }

    @PutMapping("/finish")
    public CommonReturnType finish(@RequestParam String orderId) {
        OrderMaster orderMaster = orderService.finish(orderId);
        return CommonReturnType.create(orderMaster);
    }

    @PutMapping("/cancelledByBuyer")
    public CommonReturnType cancelByBuyer(@RequestParam String orderId) {
        OrderMaster orderMaster = orderService.cancelByBuyer(orderId);
        return CommonReturnType.create(orderMaster);
    }

    private OrderDTO orderForm2OrderDTO(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerId(orderForm.getBuyerId());
        orderDTO.setBuyerName(orderForm.getBuyerName());
        orderDTO.setBuyerPhone(orderForm.getBuyerPhone());
        orderDTO.setBuyerAddress(orderForm.getBuyerAddress());

        Gson gson = new Gson();
        List<OrderItem> orderDetailList;
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderItem>>(){}.getType());
        } catch (Exception e) {
            log.error("[Convert], orderForm2OrderDTO={}", orderForm.getItems());
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        orderDTO.setOrderItemList(orderDetailList);
        return orderDTO;
    }


}
