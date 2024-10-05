package com.qifei.mapper;

import com.qifei.pojo.Supplier;
import com.qifei.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SupplierMapper {

    public Supplier selectByNamePwd(Integer id,String password);
public List<Supplier> selectSupplierByName(String name,Integer adminId);
public Integer insertSupplier(Supplier supplier);
public Integer deleteById(@Param("deleteIdList") List<Integer> deleteIdList);
public Integer updateSupplier(User supplier);
public Integer updatePassword(Integer id,String password);
}

