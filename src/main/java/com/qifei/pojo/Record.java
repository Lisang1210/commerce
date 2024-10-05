package com.qifei.pojo;

import lombok.Data;


@Data
public class Record {
    private Integer id;
    private String type;
    private Long productId;
    private Integer number;
    private String opTime;
    private Integer orderId;
    private Integer inventory;
}
