$(function () {
    $("#jqGrid").jqGrid({
        url: '/order/list',
        datatype: "json",
        colModel: [
            {label: '订单编号', name: 'id', index: 'id', width: 80},
            {label: '发起时间', name: 'createTime', index: 'createTime', width: 150,sortable:true},
            {label: '货品', name: 'productName', index: 'productId', width: 80},
            {label: '需求数量', name: 'number', index: 'number', width: 80},
            {label: '货品编号', name: 'productId', index: 'productId', width: 80,hidden:true},
/*            {label: '货品详情', name: 'productName', index: 'productName', width: 80,hidden:true},
            {label: '货品详情', name: 'productPrice', index: 'productPrice', width: 80,hidden:true},
            {label: '货品详情', name: 'productImage', index: 'productImage', width: 80,hidden:true},*/
            {label: '库存数量', name: 'inventory', index: 'inventory', width: 80},
            {label: '订单状态', name: 'status', index: 'status', width: 80,formatter:orderStatusFormatter},
            {
                label: '处理操作',
                name: 'balance',
                index: 'balance',
                width: 80,
                formatter: operateFormatter
            },
        ],
        height: 760,
        rowNum: 10,
        rowList: [20, 50, 80],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        classes: 'centerAlign',
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
            status: function () {
                return $("#selectStatus").val();
            }
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            var grid = $("#jqGrid");
            var ids = grid.jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var rowId = ids[i];
                // 为特定单元格添加自定义类
                grid.find('#' + rowId + ' td[aria-describedby="jqGrid_balance"]').addClass("ope");
            };
        }
    });

    $('#jqGrid').on('click', '.ope', function () {
        var rowId = $(this).closest('tr').attr('id');
        handleOrder(rowId);
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function operateFormatter(cellvalue) {
        if(cellvalue=="0") return `<button type=\"button\" class=\"btn\"  style=\"width: 80%\" data-value="0">已处理</button>`;
        if(cellvalue=="-1") return `<button type=\"button\" class=\"btn btn-danger\"  style=\"width: 80%\" data-value="-1">库存不足</button>`;
        if(cellvalue=="1") return `<button type=\"button\" class=\"btn btn-success\"  style=\"width: 80%\" data-value="1">出库</button>`;
    }

    function orderStatusFormatter(cellvalue)
    {
        if(cellvalue=="0")
        return "<button type=\"button\" class=\"btn btn-danger\"  style=\"width: 80%;;background-color: #d32535\">已拒绝</button>";
        if(cellvalue=="1")
            return "<button type=\"button\" class=\"btn btn-success\"  style=\"width: 80%\">待响应</button>";
        if(cellvalue=="2")
            return "<button type=\"button\" class=\"btn\"  style=\"width: 80%\">已完成</button>";
    }
});


function search() {
    $("#jqGrid").trigger('reloadGrid');
}


/**
 * 处理订单
 */

function handleOrder(rowId) {
    var rowDate=$("#jqGrid").jqGrid('getRowData', rowId);
    var inventory=rowDate.inventory-rowDate.number;
    inventory=inventory>=0?inventory:0;

    var balanceValue = $(`#${rowId} td[aria-describedby="jqGrid_balance"] button`).data('value');

    if(balanceValue!="0")
    {
        var data={
            productId:rowDate.productId,
            orderId:rowDate.id,
            number:inventory,
            type:0,
            changeNumber:rowDate.number
        }
        $.ajax({
            type:"POST",
            url: "/product/modifyNumber",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (res) {
                if (res.resultCode == 200) {
                    swal(res.message, {
                        icon: "success",
                    });
                    $("#jqGrid").trigger('reloadGrid');
                } else {
                    swal(res.message, {
                        icon: "error",
                    });
                }
            }
        });
    }
}