package com.qifei.mapper;

import com.qifei.pojo.Admin;
import com.qifei.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    public Admin selectByNamePwd(Integer id,String password);
    public Integer updateAdmin(User admin);
    public Integer updatePassword(Integer id, String password);
}
