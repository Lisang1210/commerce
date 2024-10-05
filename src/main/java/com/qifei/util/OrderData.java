package com.qifei.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@AllArgsConstructor
@Data
public class OrderData {
    private List<DateAndTotal> listFinish;
    private List<DateAndTotal> listAdd;
    private List<DateAndTotal> listTotal;//最近五天每天已完成订单支出的金额
    private Integer todayFinish;//今日完成订单数
    private Integer todayAdd;//今日新增订单数
    private Integer totalWaitHandle;//截至今日待处理订单数
}
