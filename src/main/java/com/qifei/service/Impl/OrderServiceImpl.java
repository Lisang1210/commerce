package com.qifei.service.Impl;

import com.github.pagehelper.PageHelper;
import com.qifei.common.CommonFuction;
import com.qifei.common.Constants;
import com.qifei.mapper.OrderMapper;
import com.qifei.pojo.Order;
import com.qifei.util.DateAndTotal;
import com.qifei.util.OrderData;
import com.qifei.util.orderResult;
import com.qifei.service.OrderService;
import com.qifei.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public PageResult findByStatus(Integer page, Integer pageSize,String status,Integer id,String identify) {
        PageHelper.startPage(page,pageSize);
        identify = identify.equals("采购员")?"purchaser":"supplier";
        List<orderResult> orderList=orderMapper.selectOrderByStatus(status,id,identify);
        for (orderResult orderResult : orderList) {
            if(orderResult.getStatus().equals("1"))
            {
                if(orderResult.getInventory()-orderResult.getNumber()>0)
                    orderResult.setBalance("1");
                else
                    orderResult.setBalance("-1");
            }
            else
                orderResult.setBalance("0");
        }
        return CommonFuction.getPageResult(orderList,page,pageSize);
    }

    @Override
    public boolean deleteOrder(List<Integer> deleteList) {
        if (orderMapper.deleteOrders(deleteList)>0) return true;
        return false;
    }

    @Override
    public boolean addOrder(Long productId,Integer purchaserId,Integer number,Integer inventory) {
        //添加订单
        Order order=new Order();
        order.setProductId(productId);
        order.setPurchaserId(purchaserId);
        order.setNumber(number);
        order.setStatus(Constants.ORDER_AWAIT);
        order.setInventory(inventory);
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setCreateTime(simpleDateFormat.format(date));
        order.setFinishTime("-");

        if(orderMapper.insertOrder(order)>0) return true;

        return false;
    }

    @Override
    public boolean modifyOrderStatus(Integer id, String status) {
        System.out.println("状态="+status);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(orderMapper.updateOrderStatusAndFinishTime(id,status,simpleDateFormat.format(new Date()))>0) return true;
        return false;
    }

    @Override
    public OrderData getOrderData(Integer id,String identify) {

        //近五天日期
        List<DateAndTotal> list=orderMapper.getFinishedOrderExpense(id);

        List<DateAndTotal> listFinished= orderMapper.getFinishedOrder(id);

        List<DateAndTotal> listAdd=orderMapper.getAddOrder(id);

        Integer todayFinish= orderMapper.selectFinished(id);

        Integer todayAdd=orderMapper.selectAdd(id);

        identify = identify.equals("采购员")?"purchaser":"supplier";

        Integer totalWaitHandle= orderMapper.selectAllAwait(id, identify);

        return new OrderData(listFinished,listAdd,list,todayFinish,todayAdd,totalWaitHandle);
    }

    @Override
    public boolean modifyOrderInventory(Long id, Integer number) {
        if(orderMapper.updateOrderInventory(id,number)>0) return true;
        return false;
    }

}
