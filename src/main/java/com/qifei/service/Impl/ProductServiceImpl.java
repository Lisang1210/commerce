package com.qifei.service.Impl;

import com.github.pagehelper.PageHelper;
import com.qifei.common.CommonFuction;
import com.qifei.mapper.ProductMapper;
import com.qifei.pojo.Product;
import com.qifei.util.ProductResult;
import com.qifei.service.ProductService;
import com.qifei.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public PageResult findByName(Integer page, Integer pageSize, String name,Integer supplierId) {
        PageHelper.startPage(page,pageSize);
        List<ProductResult> productList=productMapper.selectProductByName(name,supplierId);

        return CommonFuction.getPageResult(productList,page,pageSize);
    }

    @Override
    public PageResult findByNameAll(Integer page, Integer pageSize, String name) {
        PageHelper.startPage(page,pageSize);
        List<ProductResult> productList=productMapper.selectProductByNameAll(name);

        return CommonFuction.getPageResult(productList,page,pageSize);
    }
    @Override
    public Integer findNumber(Long id) {
        return productMapper.selectProductById(id);
    }

    @Override
    public boolean addProduct(Product product) {
        if(productMapper.insertProduct(product)>0) return true;
        return false;
    }

    @Override
    public boolean deleteProducts(List<Integer> deleteList) {
        if(productMapper.deleteProducts(deleteList)>0) return true;
        return false;
    }

    @Override
    public boolean modifyProduct(Product product) {
        if(productMapper.updateProduct(product)>0) return true;
        return false;
    }

    @Override
    public boolean modifyProductNumber(Long id, Integer number) {
        if(productMapper.updateProductNumber(id, number)>0) return true;
        return false;
    }
}
