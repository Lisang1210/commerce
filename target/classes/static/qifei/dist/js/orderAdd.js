$(function () {
    $("#jqGrid").jqGrid({
        url: '/product/allList',
        datatype: "json",
        colModel: [
            {label: '货物编号', name: 'id', index: 'id', width: 80},
            {label: '货物名称', name: 'name', index: 'name', width: 120},
            {label: '图片', name: 'image', index: 'image', width: 80, formatter: coverImageFormatter},
            {label: '供应商详细信息', name: 'supplierName', index: 'supplierName', width: 150, hidden: true }, // 隐藏该列
            {label: '供应商详细信息', name: 'supplierContact', index: 'supplierContact', width: 150, hidden: true },
            {label: '供应商', name: 'supplierId', index: 'supplierId', width: 80},
            {label: '单价', name: 'price', index: 'price', width: 80},
            {
                label: '操作',
                name: 'operate',
                index: 'operate',
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
            name: function () {
                return $("#searchProduct").val();
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
                grid.find('#' + rowId + ' td[aria-describedby="jqGrid_operate"]').addClass("purchase");
                // 为特定单元格添加自定义类
/*                grid.find('#' + rowId + ' td[aria-describedby="jqGrid_supplierId"]').addClass("detailSupplier");*/
            };

            $('.purchase').click(function (){
                var rowId = $(this).closest('tr').attr('id');
                addShow(rowId);
            });
/*
            $('.detailSupplier').hover(
                function (event) {
                    console.log("触摸");
                    var rowId = $(this).closest('tr').attr('id');
                    var rowData=$("#jqGrid").jqGrid('getRowData', rowId);
                    $('#tooltip').append(`<scan>${rowData.supplierName}</scan><br><scan>${rowData.supplierContact}</scan>`);
                    $('#tooltip').css({backgroundColor:'#000', top: event.clientY + 5, left: event.clientX + 5 }).fadeIn();
                },
                function () {
                    $('#tooltip scan').remove()
                    $('#tooltip br').remove()
                    $('#tooltip').fadeOut();
                }
            );*/
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function operateFormatter() {
        return `<button type=\"button\"  class=\"btn btn-danger fa fa-plus\"  style=\"width: 80%;\" >采购</button>`;
    }
    function coverImageFormatter(cellvalue) {
        return "<img src='" + cellvalue + "' height=\"80\" width=\"80\" alt='货品头像'/>";
    }
});

function calculateTotal() {
    var price = parseFloat($('#price').val()) || 0;
    var number = parseInt($('#number').val()) || 0;
    var total = price * number;
    $('#total').val(total.toFixed(2));
}
$('#price, #number').on('input', calculateTotal);
// 更新提示框位置
/*$(document).mousemove(function (event) {
    $('#tooltip').css({ top: event.pageY + 5, left: event.pageX + 5 });
});*/


/**
 * 添加
 */


function reset(rowId)
{
    var rowData=$("#jqGrid").jqGrid('getRowData', rowId);
    $("#productId").val(rowData.id);
    $("#supplierId").val(rowData.supplierId);
    $("#name").val(rowData.name);
    $("#price").val(rowData.price);
}

function addShow(rowId)
{
    reset(rowId);
    $('#addModal').modal('show');
}

function orderAdd() {
    var productId=$("#productId").val();
    var purchaserId=$('#userId').text();/*=$("#supplierId").val();*/
    var number=$("#number").val();

    var data={
        productId:productId,
        purchaserId:purchaserId,
        number:number,
    };
    $.ajax({
        type:"GET",
        url: "/order/add",
        contentType: "application/json",
        data: data,
        success: function (res) {
            if (res.resultCode == 200) {
                swal(res.message, {
                    icon: "success",
                });
                $("#number").val(1);
                $("#total").val($("#price").val());
            } else {
                swal(res.message, {
                    icon: "error",
                });
            }
        }
    })

}

function search() {
    $("#jqGrid").trigger('reloadGrid');
}