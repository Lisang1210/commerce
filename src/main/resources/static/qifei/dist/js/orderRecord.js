$(function () {
    $("#jqGrid").jqGrid({
        url: '/order/list',
        datatype: "json",
        colModel: [
            {label: '订单编号', name: 'id', index: 'id', width: 80},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 120,sortable:true},
            {label: '完成时间', name: 'finishTime', index: 'finishTime', width: 80,sortable:true},
            {label: '货品编号', name: 'productId', index: 'productId', width: 80},

            {label: '货品详情', name: 'productName', index: 'productName', width: 80,hidden:true},
            {label: '货品详情', name: 'productPrice', index: 'productPrice', width: 80,hidden:true},
            {label: '货品详情', name: 'productImage', index: 'productImage', width: 80,hidden:true},

            {label: '采购数量', name: 'number', index: 'number', width: 80},
            {label: '订单状态', name: 'status', index: 'status', width: 80,formatter:orderStatusFormatter}
        ],
        height: 760,
        rowNum: 10,
        rowList: [20, 50, 80],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
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
        beforeSelectRow: function(rowId) {
            // 假设某些行根据ID不能被选中
            var balanceValue = $(`#${rowId} td[aria-describedby="jqGrid_status"] button`).data('value');

            if (balanceValue == "1")  {
                swal("操作失败", {
                    icon: "error",
                    text: "【待响应】订单无法删除！！！"
                });
                return false;  // 阻止选择这些行
            }
            return true;  // 允许选择其他行
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});

        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function operateFormatter(cellvalue,option,rowObject) {
        return "<button type=\"button\" class=\"btn btn-danger fa fa-trash-o\"  style=\"width: 80%;\" onclick=\"deletedOrder('" + rowObject.id + "')\">删除</button>";
    }

    function orderStatusFormatter(cellvalue)
    {
        if(cellvalue=="0")
        return "<button type=\"button\" class=\"btn btn-danger\"  style=\"width: 80%;\" data-value=\"0\">已拒绝</button>";
        if(cellvalue=="1")
            return "<button type=\"button\" class=\"btn btn-success\"  style=\"width: 80%;\" data-value=\"1\">待响应</button>";
        if(cellvalue=="2")
            return "<button type=\"button\" class=\"btn\"  style=\"width: 80%;\" data-value=\"2\">已完成</button>";
    }

    function calculateTotal() {
        var price = parseFloat($('#price').val()) || 0;
        var number = parseInt($('#number').val()) || 0;
        var total = price * number;
        $('#total').val(total.toFixed(2));
    }
    $('#price, #number').on('input', calculateTotal);
});


/**
 * 添加
 */


function search() {
    $("#jqGrid").trigger('reloadGrid');
}


/**
 * 删除
 */

function deleted() {
    var ids=getSelectedRows();
    if(ids==null||ids.length==0)
    {
        swal("操作失败", {
            icon: "error",
            text: "请选择您要操作的数据！！！"
        });
    }
    else{
        swal({
            title: "确认弹框",
            text: "确认删除Id为【'"+ids+"'】的采购员吗?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then((flag) => {
                if (flag) {
                    $.ajax({
                        type:"POST",
                        url: "/purchaser/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
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
        );
    }
}