package com.qifei.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qifei.common.CommonFuction;
import com.qifei.mapper.SupplierMapper;
import com.qifei.pojo.Supplier;
import com.qifei.pojo.User;
import com.qifei.service.SupplierService;
import com.qifei.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public Supplier findBynamePwd(Integer id,String password) {
        return supplierMapper.selectByNamePwd(id, password);
    }

    @Override
    public PageResult findByName(Integer page,Integer pageSize,String name,Integer adminId) {
        PageHelper.startPage(page,pageSize);
        List<Supplier> supplierList=supplierMapper.selectSupplierByName(name,adminId);
        return CommonFuction.getPageResult(supplierList,page,pageSize);
    }

    @Override
    public boolean addSupplier(Supplier supplier) {
        if(supplierMapper.insertSupplier(supplier)>0)  return true;
        return false;
    }

    @Override
    public boolean deleteSuppliers(List<Integer> deleteIdList) {
        if(supplierMapper.deleteById(deleteIdList)==deleteIdList.size())
            return true;
        return false;
    }

    @Override
    public boolean modifySupplier(User supplier) {
        if(supplierMapper.updateSupplier(supplier)>0) return true;
        return false;
    }

    @Override
    public boolean modifyPassword(Integer id, String password) {
        if(supplierMapper.updatePassword(id, password)>0) return true;
        return false;
    }
}
