package com.qifei.pojo;


import lombok.Data;

@Data
public class Product {
    private Long  id;
    private String name;
    private Double price;
    private String image;
    private Integer number;
    private Integer supplierId;
    private Supplier supplier;
}
