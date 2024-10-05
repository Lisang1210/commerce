package com.qifei;

import com.github.pagehelper.PageHelper;
import com.qifei.mapper.OrderMapper;
import com.qifei.mapper.ProductMapper;
import com.qifei.mapper.SupplierMapper;
import com.qifei.pojo.Order;
import com.qifei.pojo.Product;
import com.qifei.pojo.Supplier;
import com.qifei.service.SupplierService;
import com.qifei.util.PageResult;
import com.qifei.util.Result;
import com.qifei.util.ResultGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class QifeiApplicationTests {

/*  @Autowired
  private OrderMapper orderMapper;*/
  @Autowired
  private ProductMapper productMapper;

    @Test
    void contextLoads() throws ParseException {
/*
      List<Product> list= productMapper.selectProduct();
      for (Product product : list) {
        System.out.println(product);*/
      }
/*      Order order=new Order();
      order.setProductId(10000L);
      order.setPurchaserId(2);
      order.setNumber(2);
      order.setStatus("待处理");
      Date date=new Date();
      SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      order.setCreateTime(simpleDateFormat.format(date));
      order.setFinishTime("-");
      System.out.println(LocalDateTime.now());*/

/*      Date date=new Date();
      SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      DateTimeFormatter df=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      String dateStr=simpleDateFormat.format(date);
      System.out.println(LocalDateTime.parse(dateStr,df));*/
/*      Date date=new Date();

      SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      System.out.println(simpleDateFormat.format(date));
      System.out.println(date);
      System.out.println(simpleDateFormat.parse("2024-05-29 01:02:03"));*/
  /*    System.out.println(simpleDateFormat.parse());*/
/*      orderMapper.insertOrder(order);*/

   /*   Date date=new Date();
      SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      simpleDateFormat.format(date);*/

/*
    }*/
}
