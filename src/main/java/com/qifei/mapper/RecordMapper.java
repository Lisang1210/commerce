package com.qifei.mapper;

import com.qifei.pojo.Record;
import com.qifei.util.DateAndTotal;
import com.qifei.util.RecordResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecordMapper {

    //返回根据类型返回对应记录
    public List<RecordResult> selectRecord(String type,String name,Integer supplierId);

    public Integer insertRecordProduct(Record record);

    public Integer insertRecordOrder(Record record);

    //删除记录
    public Integer deleteRecords(@Param("deleteIdList")List<Integer> deleteIdList);

    public List<DateAndTotal> getOutProduct(Integer supplierId);
    public List<DateAndTotal> getInProduct(Integer supplierId);

    public Integer selectIn(Integer supplierId);
    public Integer selectOut(Integer supplierId);
}
