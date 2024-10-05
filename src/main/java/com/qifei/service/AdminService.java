package com.qifei.service;

import com.qifei.pojo.Admin;
import com.qifei.pojo.User;
import org.springframework.stereotype.Service;


public interface AdminService {
    public Admin findByNamePwd(Integer id,String password);
    public boolean modifyAdmin(User admin);
    public boolean modifyPassword(Integer id,String password);
}
