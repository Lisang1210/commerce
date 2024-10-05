package com.qifei.pojo;

import lombok.Data;

@Data
public class HandleOrder {
    private Integer orderId;
    private Long productId;
    private Integer type;
    private Integer number;
    private Integer changeNumber;
}
