package com.qifei.service;

import com.qifei.pojo.Record;
import com.qifei.util.OrderData;
import com.qifei.util.PageResult;
import com.qifei.util.ProductData;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RecordService {
    public PageResult recordList(String type,Integer page, Integer pageSize,String name,Integer supplierId);
    public boolean addRecord(Integer orderId,Long productId,Integer number,Integer inventory,String type);
    public boolean deleteRecords(List<Integer> deleteList);
    public ProductData getProductData(Integer supplierId,String identify);
}
