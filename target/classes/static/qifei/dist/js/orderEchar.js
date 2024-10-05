$(function (){

    var dateList=[];
    var amountList=[];
    var dataAddList=[];
    var dataFinishedList=[];
    $.ajax({
        type:"GET",
        url: "/order/orderData",
        contentType: "application/json",
        success: function (res) {
            if (res.listTotal!=null) {
                var listTotal = res.listTotal;
                for (i = 0; i < listTotal.length; ++i) {
                    dateList.push(listTotal[i].finishDate);
                    amountList.push(listTotal[i].total);
                }
            }
            if(res.listFinish!=null)
            {
                var listFinish = res.listFinish;
                for (i = 0; i < listFinish.length; ++i) {
                    dataFinishedList.push(listFinish[i].number);
                }
            }
            if(res.listAdd!=null)
            {
                var listAdd = res.listAdd;
                for (i = 0; i < listAdd.length; ++i) {
                    dataAddList.push(listAdd[i].number);
                }
            }
                $('#todayFinish').text(res.todayFinish);
                $('#todayAdd').text(res.todayAdd);
                $('#allWait').text(res.totalWaitHandle);
                // 基于准备好的 dom，初始化 echarts 实例
                var myChart = echarts.init(document.getElementById('main1'));

            // 指定图表的配置项和数据
            var option = {
                grid: {
                    left: '2%',
                    right: '2%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    name: "日期",
                    data:dateList
                },
                yAxis: { name: "每日已完成订单金额" },
                series: [
                    {
                        data: amountList,
                        type: "bar",
                        label: {
                            show: true,
                            position: "top",
                            formatter: "{c}",
                        },
                        itemStyle: {
                            color: function (params) {
                                // 定义颜色数组
                                var colors = ["#B0E2FF", "#87CEFA", "#6495ED", "#4169E1","#0069b7","#054e8a","#000080"];
                                return colors[params.dataIndex % colors.length];
                            },
                        },
                    },
                    {
                        type: "line",
                        data: amountList,
                        itemStyle: {
                            color:"#0069b7"
                        }
                    },
                ],
                backgroundColor: "#fff"
            };
                myChart.setOption(option);

                // 基于准备好的 dom，初始化 echarts 实例
                var myChart = echarts.init(document.getElementById('main2'));
                // 指定图表的配置项和数据
                var option = {
                    xAxis: {
                        name: "日期",
                        data:dateList
                    },
                    legend: {
                        data: ['每日新增订单', '每日已完成订单']
                    },
                    grid: {
                        left: '2%',
                        right: '2%',
                        bottom: '3%',
                        containLabel: true
                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: dateList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            name: '每日新增订单',
                            type: 'line',
                            stack: 'Total',
                            data: dataAddList,
                            label: {
                                show: true,
                                position: "top",
                                formatter: "{c}",
                            },
                            itemStyle: {
                                color:"#058cf1"
                            }
                        },
                        {
                            name: '每日已完成订单',
                            type: 'line',
                            stack: 'Total',
                            data: dataFinishedList,
                            label: {
                                show: true,
                                position: "top",
                                formatter: "{c}",
                            },
                            itemStyle: {
                                color:"#f10a25"
                            }
                        }
                    ],
                    backgroundColor: "#fff",
                };
                myChart.setOption(option);
            }
    });

})