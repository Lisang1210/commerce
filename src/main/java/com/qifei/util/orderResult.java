package com.qifei.util;

import lombok.Data;

@Data
public class orderResult {
    private Integer  id;
    private String createTime;
    private String finishTime;
    private String status;
    private Integer number;
    private Long productId;
    private Integer purchaserId;
    private String productName;
    private Double productPrice;
    private String productImage;
    private Integer inventory;
    private String balance;
}
