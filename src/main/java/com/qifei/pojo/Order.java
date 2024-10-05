package com.qifei.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Order {
    private Integer  id;
    private String createTime;
    private String finishTime;
    private String status;
    private Integer number;
    private Integer inventory;
    private Long productId;
    private Integer purchaserId;
    private Product product;
}
