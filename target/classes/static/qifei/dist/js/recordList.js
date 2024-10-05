$(function () {
    $("#jqGrid").jqGrid({
        url: '/record/list',
        datatype: "json",
        colModel: [
            {label: '货物编号', name: 'productId', index: 'productId', width: 80},
            {label: '货物名称', name: 'productName', index: 'productName', width: 80},
            {label: '货物图片', name: 'productImage', index: 'productImage', width: 80,formatter:imageFormatter},
            {label: '处理后库存', name: 'inventory', index: 'inventory', width: 60},
            {label: '变更数量', name: 'number', index: 'number', width: 80},
            {label: '操作时间', name: 'opTime', index: 'opTime', width: 80},
        ],
        height: 760,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        postData:{
            name:function (){
                return $('#searchProduct').val()
            }
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});

        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function imageFormatter(cellvalue){
        return "<img src='" + cellvalue + "' height=\"80\" width=\"80\" alt='货物头像'/>";
    }
});

function search()
{
    $("#jqGrid").trigger('reloadGrid');
}

