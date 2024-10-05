package com.qifei.mapper;

import com.qifei.pojo.Product;
import com.qifei.util.ProductResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    //根据名字检索商品
    public List<ProductResult> selectProductByName(String name,Integer supplierId);


    public List<ProductResult> selectProductByNameAll(String name);
    //根据id检索商品
    public Integer selectProductById(Long id);


    //供应商添加商品
    public Integer insertProduct(Product product);

    //供应商移除商品
    public Integer deleteProducts(@Param("deleteIdList")List<Integer> deleteIdList);

    //供应商修改商品基本信息
    public Integer updateProduct(Product product);

    //供应商添加已存在商品数量
    public Integer updateProductNumber(Long id,Integer number);
}
