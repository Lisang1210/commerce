package com.qifei.mapper;

import com.qifei.pojo.Order;
import com.qifei.util.DateAndTotal;
import com.qifei.util.orderResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    //根据订单状态查找订单
    public List<orderResult> selectOrderByStatus(String status,Integer id,String identify);

    //采购员删除订单(未处理的订单无法删除)
    public Integer deleteOrders(@Param("deleteIdList")List<Integer> deleteList);

    //采购员添加订单
    public Integer insertOrder(Order order);

    //供应商处理订单（修改订单状态）
    public Integer updateOrderStatusAndFinishTime(Integer id,String status,String finishTime);

    //获取近五天已完成订单花费的金额
    public List<DateAndTotal> getFinishedOrderExpense(Integer id);


    public Integer updateOrderInventory(Long id,Integer number);

    public List<DateAndTotal> getFinishedOrder(Integer id);


    public List<DateAndTotal> getAddOrder(Integer id);

    public Integer selectFinished(Integer id);

    public Integer selectAdd(Integer id);

    public Integer selectAllAwait(Integer id,String identify);
}
