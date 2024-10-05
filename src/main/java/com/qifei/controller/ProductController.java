package com.qifei.controller;

import com.qifei.common.Constants;
import com.qifei.pojo.HandleOrder;
import com.qifei.pojo.Order;
import com.qifei.pojo.Product;
import com.qifei.pojo.User;
import com.qifei.service.OrderService;
import com.qifei.service.ProductService;
import com.qifei.service.RecordService;
import com.qifei.util.OrderData;
import com.qifei.util.ProductData;
import com.qifei.util.Result;
import com.qifei.util.ResultGenerator;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam(defaultValue = "") String name, HttpSession session){
        if (Objects.isNull(page)||Objects.isNull(limit)) {
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        }
        return ResultGenerator.genSuccessResult(productService.findByName(page, limit, name,((User) session.getAttribute("User")).getId()));
    }

    @RequestMapping(value = "/allList",method = RequestMethod.GET)
    @ResponseBody
    public Result allList(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam(defaultValue = "") String name){
        if (Objects.isNull(page)||Objects.isNull(limit)) {
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        }
        return ResultGenerator.genSuccessResult(productService.findByNameAll(page, limit, name));
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result addProduct(@RequestParam Product product){
        if (Objects.isNull(product)) {
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        }
            if(productService.addProduct(product))
                return ResultGenerator.genSuccessResult(Constants.ADD_SUCCESS);
        return ResultGenerator.genFailResult(Constants.ADD_FAILURE);
    }

    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    @ResponseBody
    public Result modifyProduct(@RequestParam Product product){
        if (Objects.isNull(product)) {
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        }
        if (productService.modifyProduct(product))
          return ResultGenerator.genSuccessResult(Constants.MODIFY_SUCCESS);
        return ResultGenerator.genFailResult(Constants.MODIFY_FAILURE);
    }

    @RequestMapping(value = "/modifyNumber",method = RequestMethod.POST)
    @ResponseBody
    public Result modifyProduct(@RequestBody HandleOrder handleOrder){

        if (Objects.isNull(handleOrder)) {
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        }

        //number是修改后的值
        switch (handleOrder.getType())
        {
            //减少货品数量（分两种情况：一种是供应商减少货物数量；一种是供应商处理订单）
            case 0:
                if(handleOrder.getNumber()==0)//若库存不足（一定是处理订单），没有商品出库，商品数量也没有变化，只需要修改订单状态
                {
                    //修改订单状态
                    if(orderService.modifyOrderStatus(handleOrder.getOrderId(),Constants.ORDER_REJECT))
                        return ResultGenerator.genSuccessResult(Constants.HANDLE_SUCCESS);
                }
                else
                {
                    //此时一定是库存充足，首先修改商品数量
                    if (productService.modifyProductNumber(handleOrder.getProductId(), handleOrder.getNumber()));
                    {
                        //其次添加出库记录
                        if(recordService.addRecord(handleOrder.getOrderId(),handleOrder.getProductId(),handleOrder.getChangeNumber(),handleOrder.getNumber(),Constants.OUT_Storage));
                        {
                            //最后判断是否是处理订单，若是商品出库是因为处理订单，则修改状态，否则不变
                            //订单商品出库，需要修改订单状态(此时orderId和productId都不0)
                            if(handleOrder.getOrderId()!=0 && orderService.modifyOrderStatus(handleOrder.getOrderId(),Constants.ORDER_FINISH));

                            //若是供应商减少货物数量，则不用修改订单状态（此时orderId为0）
                             return    ResultGenerator.genSuccessResult(Constants.HANDLE_SUCCESS);
                        }
                    }
                }
            break;

            //添加货物数量
            case 1:
                if (productService.modifyProductNumber(handleOrder.getProductId(), handleOrder.getNumber()));
            {
                if(recordService.addRecord(handleOrder.getOrderId(), handleOrder.getProductId(), handleOrder.getChangeNumber(),handleOrder.getNumber(),Constants.IN_Storage))
                {if(orderService.modifyOrderInventory(handleOrder.getProductId(),handleOrder.getNumber()));
                    return    ResultGenerator.genSuccessResult(Constants.HANDLE_SUCCESS);
                }
            }
                break;
        }
        return ResultGenerator.genFailResult(Constants.HANDLE_FAILURE);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] params) {

        if (StringUtils.isEmpty(params)||Objects.isNull(params)) {
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        }

        List<Integer> list= Arrays.asList(params);
        if(productService.deleteProducts(list))
            return ResultGenerator.genSuccessResult(Constants.OFFSHELF_SUCCESS);
        return ResultGenerator.genFailResult(Constants.OFFSHELF_FAILURE);
    }
}
