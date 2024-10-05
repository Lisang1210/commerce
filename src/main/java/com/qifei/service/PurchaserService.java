package com.qifei.service;

import com.qifei.pojo.Purchaser;
import com.qifei.pojo.Supplier;
import com.qifei.pojo.User;
import com.qifei.util.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



public interface PurchaserService {
    public Purchaser findByNamePwd(Integer id,String password);
    public boolean addPurchaser(Purchaser purchaser);
    public PageResult findByName(Integer page,Integer pageSize,String name,Integer adminId);
    public boolean deletePurchasers(List<Integer> deleteIdList);
    public boolean modifyPurchaser(User purchaser);
    public boolean modifyPassword(Integer id,String password);
}
