$(function (){

    var dateInList=[];
    var dateOutList=[];
    var dataOutList=[];
    var dataInList=[];
    $.ajax({
        type:"GET",
        url: "/record/productData",
        contentType: "application/json",
        success: function (res) {
            if (res.listIn!=null) {
                var listIn = res.listIn;
                for (i = 0; i < listIn.length; ++i) {
                    dateInList.push(listIn[i].finishDate);
                    dataInList.push(listIn[i].total);
                }
            }
            if(res.listOut!=null)
            {
                var listOut = res.listOut;
                for (i = 0; i < listOut.length; ++i) {
                    dateOutList.push(listOut[i].finishDate);
                    dataOutList.push(listOut[i].total);
                }
            }
            console.log("listIn="+res.listIn);
            console.log("listOut="+res.listOut);
            console.log("todayIn="+res.todayIn);
            console.log("todayOut="+res.todayOut);
                $('#todayIn').text(res.todayIn);
                $('#todayOut').text(res.todayOut);
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
                    data:dateOutList
                },
                yAxis: { name: "出库数量" },
                series: [
                    {
                        data: dataOutList,
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
                        data: dataOutList,
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
                grid: {
                    left: '2%',
                    right: '2%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    name: "日期",
                    data:dateInList
                },
                yAxis: { name: "入库数量" },
                series: [
                    {
                        data: dataInList,
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
                        data: dataInList,
                        itemStyle: {
                            color:"#0069b7"
                        }
                    },
                ],
                backgroundColor: "#fff"
            };
                myChart.setOption(option);
            }
    });

})