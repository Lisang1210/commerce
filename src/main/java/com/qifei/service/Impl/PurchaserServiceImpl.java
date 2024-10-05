package com.qifei.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qifei.common.CommonFuction;
import com.qifei.common.Constants;
import com.qifei.mapper.PurchaserMapper;
import com.qifei.pojo.Purchaser;
import com.qifei.pojo.User;
import com.qifei.service.PurchaserService;
import com.qifei.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PurchaserServiceImpl implements PurchaserService {
    @Autowired
    private PurchaserMapper purchaserMapper;

    @Override
    public Purchaser findByNamePwd(Integer id,String password) {
        return purchaserMapper.selectByNamePwd(id, password);
    }

    @Override
    public PageResult findByName(Integer page,Integer pageSize,String name,Integer adminId) {
        PageHelper.startPage(page,pageSize);

        List<Purchaser> purchaserList=purchaserMapper.selectPurchaserByName(name,adminId);
        return CommonFuction.getPageResult(purchaserList,page,pageSize);
    }

    @Override
    public boolean addPurchaser(Purchaser purchaser) {
        if(purchaserMapper.insertPurchaser(purchaser)>0)  return true;
        return false;
    }

    @Override
    public boolean deletePurchasers(List<Integer> deleteIdList) {
        if(purchaserMapper.deleteById(deleteIdList)==deleteIdList.size())
            return true;
        return false;
    }

    @Override
    public boolean modifyPurchaser(User purchaser) {
        if(purchaserMapper.updatePurchaser(purchaser)>0) return true;
        return false;
    }

    @Override
    public boolean modifyPassword(Integer id, String password) {
        if(purchaserMapper.updatePassword(id, password)>0) return true;
        return false;
    }
}
