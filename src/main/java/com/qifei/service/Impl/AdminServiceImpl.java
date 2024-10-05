package com.qifei.service.Impl;

import com.qifei.mapper.AdminMapper;
import com.qifei.pojo.Admin;
import com.qifei.pojo.User;
import com.qifei.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findByNamePwd(Integer id,String password) {
        return adminMapper.selectByNamePwd(id,password);
    }

    @Override
    public boolean modifyAdmin(User admin) {
        if(adminMapper.updateAdmin(admin)>0) return true;
        return false;
    }

    @Override
    public boolean modifyPassword(Integer id, String password) {
        if(adminMapper.updatePassword(id, password)>0) return true;
        return false;
    }
}
