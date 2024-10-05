package com.qifei.service;

import com.qifei.pojo.Supplier;
import com.qifei.pojo.User;
import com.qifei.util.PageResult;
import com.qifei.util.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface SupplierService {
    public Supplier findBynamePwd(Integer id,String password);
    public boolean addSupplier(Supplier supplier);
    public PageResult findByName(Integer page,Integer pageSize,String name,Integer adminId);
    public boolean deleteSuppliers(List<Integer> deleteIdList);
    public boolean modifySupplier(User supplier);
    public boolean modifyPassword(Integer id,String password);
}
