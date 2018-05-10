package com.tony.controller;
/*The order controller on the seller side*/

import com.tony.dto.OrderDTO;
import com.tony.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * The list of orders.
     * @param page number of the current page, starting from 1
     * @param size how many records are included in one page.
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page" , defaultValue = "1") Integer page,
                             @RequestParam(value = "size" , defaultValue = "10") Integer size,
                             Map<String, Object> map){
        PageRequest request =  PageRequest.of(page , size);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        map.put("orderDOTPage", orderDTOPage);
        return new ModelAndView("order/list",map);
    }
}
