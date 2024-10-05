package com.qifei.mapper;

import com.qifei.pojo.Purchaser;
import com.qifei.pojo.Supplier;
import com.qifei.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchaserMapper {

    public Purchaser selectByNamePwd(Integer id,String password);
public List<Purchaser> selectPurchaserByName(String name,Integer adminId);
public Integer insertPurchaser(Purchaser purchaser);
public Integer deleteById(@Param("deleteIdList") List<Integer> deleteIdList);

    public Integer updatePurchaser(User purchaser);
    public Integer updatePassword(Integer id,String password);
}

