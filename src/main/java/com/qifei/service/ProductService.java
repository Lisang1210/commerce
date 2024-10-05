package com.qifei.service;

import com.qifei.pojo.Product;
import com.qifei.util.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;



public interface ProductService {
    public PageResult findByNameAll(Integer page,Integer pageSize,String name);
    public PageResult findByName(Integer page,Integer pageSize,String name,Integer supplierId);
    public Integer findNumber(Long id);
    public boolean addProduct(Product product);
    public boolean deleteProducts(List<Integer> deleteList);
    public boolean modifyProduct(Product product);
    public boolean modifyProductNumber(Long id,Integer number);
}
