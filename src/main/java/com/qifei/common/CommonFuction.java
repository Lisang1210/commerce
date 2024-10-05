package com.qifei.common;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qifei.pojo.Admin;
import com.qifei.pojo.Purchaser;
import com.qifei.pojo.Supplier;
import com.qifei.pojo.User;
import com.qifei.util.PageResult;

import java.util.List;

public class CommonFuction {

    //获取分页数据
        public static <T> PageResult  getPageResult(List<T> list,Integer page,Integer pageSize)
        {
            Page<T> p=(Page<T>) list;
            PageResult pageResult=new PageResult(p.getTotal(),p.getPageSize(),p.getPages(),p.getPageNum(),p.getResult());
            return pageResult;
        }

        //将管理员、供应商、采购员信息存储到User对象
    public static User transformUser(Admin user)
    {
        if(user==null) return null;
       return new User(user.getId(),user.getName(),user.getContact(),user.getImage(),"管理员");
    }

    public static User transformUser(Purchaser user)
    {
        if(user==null) return null;
        return new User(user.getId(),user.getName(),user.getContact(),user.getImage(),"采购员");
    }

    public static User transformUser(Supplier user)
    {
        if(user==null) return null;
        return new User(user.getId(),user.getName(),user.getContact(),user.getImage(),"供应商");
    }
}
