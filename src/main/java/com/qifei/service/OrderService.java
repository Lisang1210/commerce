package com.qifei.service;

import com.qifei.pojo.Order;
import com.qifei.util.OrderData;
import com.qifei.util.PageResult;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.stereotype.Service;

import java.util.List;



public interface OrderService {
    public PageResult findByStatus(Integer page, Integer pageSize,String status,Integer id,String identify);
    public boolean deleteOrder(List<Integer> deleteList);
    public boolean addOrder(Long productId,Integer purchaserId,Integer number,Integer inventory);
    public boolean modifyOrderStatus(Integer id,String status);
    public OrderData getOrderData(Integer id,String identify);
    public boolean modifyOrderInventory(Long id,Integer number);
}
