package com.tony.controller;

import com.tony.converter.OrderForm2OrderDTOConverter;
import com.tony.dto.OrderDTO;
import com.tony.enums.ExceptionEnum;
import com.tony.exception.SellException;
import com.tony.form.OrderForm;
import com.tony.service.Impl.BuyerServiceImpl;
import com.tony.service.Impl.OrderServiceImpl;
import com.tony.utils.ResultVOUtil;
import com.tony.viewobject.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private BuyerServiceImpl buyerService;
    //Create order

    @PostMapping("/create")
    public ResultVO <Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("[create forms] , incorrect inputs parameters. orderFrom={}", orderForm);
            throw new SellException(ExceptionEnum.ORDER_STATUS_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[create forms] , shopping cart is empty. orderFrom={}", orderForm);
            throw new SellException(ExceptionEnum.CART_EMPTY);
        }
        OrderDTO result = orderService.create(orderDTO);

        Map<String, String> map =new HashMap<>();
        map.put("orderId", result.getOrderId());
        return ResultVOUtil.success(map);
    }

    @RequestMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid, @RequestParam(value="page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("[Get order list], openid is empty.");
            throw new SellException(ExceptionEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);
        return  ResultVOUtil.success(orderDTOPage.getContent());
    }

    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid, @RequestParam("orderid") String orderid){
        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderid);
        return ResultVOUtil.success(orderDTO);
    }

    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid, @RequestParam("orderid") String orderid){
        buyerService.cancelOrder(openid,orderid);
        return ResultVOUtil.success();
    }
}
