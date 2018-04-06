package com.tony.service.Impl;

import com.tony.converter.OrderMaster2OrderDTOConverter;
import com.tony.dataobject.OrderDetail;
import com.tony.dataobject.OrderMaster;
import com.tony.dataobject.ProductInfo;
import com.tony.dto.CartDTO;
import com.tony.dto.OrderDTO;
import com.tony.enums.ExceptionEnum;
import com.tony.enums.OrderStatusEnum;
import com.tony.enums.PayStatusEnum;
import com.tony.exception.SellException;
import com.tony.repository.OrderDetailRepository;
import com.tony.repository.OrderMasterRepository;
import com.tony.service.OrderService;
import com.tony.service.ProductInfoService;
import com.tony.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    ProductInfoService productInfoService;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional   //use transaction here since just in case any action is failed, then nothing should be made into database.
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal totalPrice = new BigDecimal(BigInteger.ZERO);
        String orderId = KeyUtil.genUniqueKey();
        //Query the price and quantity of product, if there is enough in stock.
        for(OrderDetail orderDetail: orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
            }

            //Calculate the total price of the whole order.
            //Total price = price*quantity
            totalPrice = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(totalPrice);

            //Write orderDetail into database

            //Alert, copy operation shold be done first, and then set the order id and detail id.
            //Otherwise, the id will be swipped to null.
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetailRepository.save(orderDetail);
        }

        //Write orderMaster into database
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(totalPrice);
        orderMasterRepository.save(orderMaster);
        //decrease the quantity in stock.
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e->
            new CartDTO(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if(orderMaster == null){
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(orderDetailList == null){
            throw new SellException(ExceptionEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();
        //Check the current status of the order first.
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[Cancel orders] Order status is incorrect and cannot be cancelled, orderId={}, orderStatus={}", orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ExceptionEnum.ORDER_STATUS_ERROR);
        }
        //Change the order status to cancelled.
        orderDTO.setOrderStatus(OrderStatusEnum.CANCELLED.getCode());

        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("[Cancel orders] Update failed, cannot change the order status to cancelled, orderMaster={}", orderMaster);
            throw new SellException(ExceptionEnum.ORDER_UPDATE_FAIL);
        }

        //verify the stock
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[Cancel orders] There is no order exists, orderDTO={}",orderDTO);
            throw new SellException(ExceptionEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e->new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);
        //If paid, then need to refund to the user.

        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //check the order status.
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[Finish the order] , order cannot be finished due to incorrect status. orderId={}, orderStatus={}", orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ExceptionEnum.ORDER_STATUS_ERROR);
        }

        //change the order status.
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);

        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("[Finish the order] , order cannot be updated. orderMaster={}", orderMaster);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //Check order status.
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[Pay the order] , order cannot be paid due to incorrect status. orderId={}, orderStatus={}", orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ExceptionEnum.ORDER_STATUS_ERROR);
        }

        //Check payment status.
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.UNPAID.getCode())){
            log.error("[Pay the order] , the pay status is incorrect, orderDTO={}" , orderDTO);
            throw new SellException(ExceptionEnum.ORDER_PAY_STATUS_ERROR);
        }
        //Change payment status.
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("[Pay the order] , cannot save the updated info. orderMaster={}", orderMaster);
            throw new SellException(ExceptionEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
