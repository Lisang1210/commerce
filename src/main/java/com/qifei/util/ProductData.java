package com.qifei.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ProductData {
    private List<DateAndTotal> listIn;
    private List<DateAndTotal> listOut;//最近五天每天已完成订单支出的金额
    private Integer totalWaitHandle;//待处理订单数
    private Integer todayIn;
    private Integer todayOut;
}
