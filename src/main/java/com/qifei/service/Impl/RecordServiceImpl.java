package com.qifei.service.Impl;

import com.github.pagehelper.PageHelper;
import com.qifei.common.CommonFuction;
import com.qifei.mapper.OrderMapper;
import com.qifei.mapper.RecordMapper;
import com.qifei.pojo.Record;
import com.qifei.service.RecordService;
import com.qifei.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public PageResult recordList(String type,Integer page, Integer pageSize,String name,Integer supplierId) {
        PageHelper.startPage(page,pageSize);
        List<RecordResult> recordList = recordMapper.selectRecord(type,name,supplierId);
        return CommonFuction.getPageResult(recordList,page,pageSize);
    }


    @Override
    public boolean addRecord(Integer orderId,Long productId, Integer number,Integer inventory,String type) {
        Record record=new Record();
        record.setNumber(number);
        record.setProductId(productId);
        record.setType(type);
        record.setOrderId(orderId);
        record.setInventory(inventory);

        //时间设置
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        record.setOpTime(simpleDateFormat.format(new Date()));

        if(orderId==0)
        {
            if(recordMapper.insertRecordProduct(record)>0) return true;
        }
        else {
            if(recordMapper.insertRecordOrder(record)>0) return true;
        }

        return false;
    }

    @Override
    public boolean deleteRecords(List<Integer> deleteList) {
        if(recordMapper.deleteRecords(deleteList)>0) return true;
        return false;
    }

    @Override
    public ProductData getProductData(Integer supplierId,String identify) {


        List<DateAndTotal> listIn= recordMapper.getInProduct(supplierId);

        List<DateAndTotal> listOut=recordMapper.getOutProduct(supplierId);

        identify = identify.equals("采购员")?"purchaser":"supplier";

        Integer totalWaitHandle= orderMapper.selectAllAwait(supplierId, identify);

        Integer todayIn= recordMapper.selectIn(supplierId);

        todayIn=todayIn==null?0:todayIn;

        Integer todayOut = recordMapper.selectOut(supplierId);

        todayOut=todayOut==null?0:todayOut;

        return new ProductData(listIn,listOut,totalWaitHandle,todayIn,todayOut);
    }


}
