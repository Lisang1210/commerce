package com.qifei.controller;

import com.qifei.common.Constants;
import com.qifei.pojo.*;
import com.qifei.service.OrderService;
import com.qifei.service.ProductService;
import com.qifei.service.RecordService;
import com.qifei.util.OrderData;
import com.qifei.util.Result;
import com.qifei.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam(defaultValue = "") String status, HttpSession session)
    {
        if (Objects.isNull(page)||Objects.isNull(limit)) {
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        }
        User user=(User) session.getAttribute("User");
        return ResultGenerator.genSuccessResult(orderService.findByStatus(page, limit, status,user.getId(),user.getIdentify()));
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public Result add(@RequestParam Long productId,@RequestParam Integer purchaserId,@RequestParam Integer number) {


        if (Objects.isNull(productId)||Objects.isNull(purchaserId)||Objects.isNull(number)) {
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        }
        Integer inventory=productService.findNumber(productId);
        if(orderService.addOrder(productId, purchaserId, number,inventory))
            return ResultGenerator.genSuccessResult(Constants.ADD_SUCCESS);
        return ResultGenerator.genFailResult(Constants.ADD_FAILURE);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] params) {
        if (StringUtils.isEmpty(params)||Objects.isNull(params)) {
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        }
        List<Integer> list= Arrays.asList(params);
        if(orderService.deleteOrder(list))
            return ResultGenerator.genSuccessResult(Constants.DELETE_SUCCESS);
        return ResultGenerator.genFailResult(Constants.DELETE_FAILURE);
    }

    @RequestMapping(value = "/orderData", method = RequestMethod.GET)
    @ResponseBody
    public OrderData orderData(HttpSession session) {

        User user=(User) session.getAttribute("User");
        return orderService.getOrderData(user.getId(), user.getIdentify());
    }
}
