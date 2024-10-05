package com.qifei.util;

import lombok.Data;

@Data
public class RecordResult {
    private Integer orderId;
    private String opTime;
    private Long productId;
    private String productName;
    private String productImage;
    private Integer number;
    private Integer inventory;
}
