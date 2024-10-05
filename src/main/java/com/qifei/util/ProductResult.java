package com.qifei.util;

import com.qifei.util.PageResult;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ProductResult {
    private Long  id;
    private String name;
    private Double price;
    private String image;
    private Integer number;
    private Integer supplierId;
    private String supplierName;
    private String supplierContact;
}
