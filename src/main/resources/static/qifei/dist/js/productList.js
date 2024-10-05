$(function () {
    $("#jqGrid").jqGrid({
        url: '/product/list',
        datatype: "json",
        colModel: [
            {label: '货物编号', name: 'id', index: 'id', width: 80},
            {label: '货物名称', name: 'name', index: 'name', width: 80},
            {label: '货物图片', name: 'image', index: 'image', width: 80,formatter:imageFormatter},
            {label: '价格', name: 'price', index: 'price', width: 80},
            {label: '供应商详情', name: 'supplierName', index: 'supplierName', width: 80,hidden:true},
            {label: '供应商详情', name: 'supplierId', index: 'supplierId', width: 80,hidden:true},
            {label: '供应商详情', name: 'supplierContact', index: 'supplierContact', width: 80,hidden:true},
            {label: '库存', name: 'number', index: 'number', width: 80},
           {
                label: '修改货品信息',
                name: 'modify',
                index: 'modify',
                width: 80,
                formatter: modifyFormatter
            },
            {
                label: '出-入库',
                name: 'addOrsub',
                index: 'addOrsub',
                width: 80,
                formatter: addOrsubFormatter
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
                grid.find('#' + rowId + ' td[aria-describedby="jqGrid_modify"]').addClass("modi");
                grid.find('#' + rowId + ' td[aria-describedby="jqGrid_addOrsub"]').addClass("aOb");
       };

            $('.modi').click(function (){
                var rowId = $(this).closest('tr').attr('id');
                modifyShow(rowId,1)
            });

            $('.aOb').click(function (){
                var rowId = $(this).closest('tr').attr('id');
                modifyShow(rowId,2)
            });
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function imageFormatter(cellvalue){
        return `<img src='${cellvalue}' height=\"80\" width=\"80\" alt='货物头像' data-value=${cellvalue}/>`;
    }

    function modifyFormatter() {
        return `<button type=\"button\" class=\"btn btn-info\"  style=\"width: 80%;\">修改</button>`;
    }

    function addOrsubFormatter()
    {
        return `<button type=\"button\" class=\"btn btn-success\"  style=\"width: 80%;\" >出/入库</button>`;
    }
});

function calculateTotal() {
    var type=$('input[name="inout"]:checked').val();
    if(type==1||type==0)
    {
        var modify = parseInt($('#modifyNumber').val()) || 0;
        var number = parseInt($('#productNumber').val()) || 0;
        var total = 0;

        if($('input[name="inout"]:checked').val()==1)
            total=modify+number;
        else
            total=number-modify;
        if(total<0)
        {
            alert("库存数量为0，不能再减少库存！！！");
            $('#modifyNumber').val($('#productNumber').val());
        }
        else
            $('#predictedNumber').val(total);
    }
}

$('#modifyNumber,#productNumber').on('input', calculateTotal);

/*
 *
 * 检索货物
 */
function search() {
    $("#jqGrid").trigger('reloadGrid');
}

/*
*
*设置修改框的值
*/
function reset(rowId,type)
{
    console.log("进入");
    var rowData=$("#jqGrid").jqGrid('getRowData', rowId);
    if(type==1)
    {
        console.log("prooctModifyImage="+$(`#${rowId} td[aria-describedby="jqGrid_image"] img`).data('value'));

        $("#id").val(rowData.id);
        $("#name").val(rowData.name);
        $("#carouselImg").attr('src', $(`#${rowId} td[aria-describedby="jqGrid_image"] img`).data('value'));
        $("#price").val(rowData.price);
        $("#number").val(rowData.number);
        $("#supplierName").val(rowData.supplierName);
    }
    else
    {
        $("#productId").val(rowData.id);
        $("#productName").val(rowData.name);
        $("#productPrice").val(rowData.price);
        $("#productNumber").val(rowData.number);
    }
}

/*
*
* 展示修改框
*/
function modifyShow(modifyId,type) {

    console.log("modifyNumber="+type);
    var ids = getSelectedRows();
    if (ids != null && ids.length == 1 && modifyId != null && modifyId != ids[0]) {
        swal("操作失败", {
            icon: "error",
            text: "勾选的数据【'" + ids[0] + "'】和要修改的数据【'" + modifyId + "'】不是同一个数据，无法完成操作！！！"
        });
    } else if (ids != null && ids.length > 1 && modifyId != null) {
        swal("操作失败", {
            icon: "error",
            text: "单行数据的【修改】按钮只能修改该行数据，无法进行批量修改！！！"
        });
    } else  if(ids==null||ids.length==0)
    {
        swal("操作失败", {
            icon: "error",
            text: "请选择您要操作的数据！！！"
        });
    } else {
        if (type == 1) {
            console.log("即将进入。。。");
            reset(modifyId, type);
            calculateTotal();
            $('#modifyModel').modal('show');
        } else {
            reset(modifyId, type);
            calculateTotal();
            $('#modifyNumberModel').modal('show');
        }
    }
}

/*
*
* 修改货品信息
 */

function modify()
{
        var data={
            id:$("#id").val(),
            name:$("#name").val(),
            image: $("#carouselImg").attr('src'),
            price:$("#price").val(),
        };
        $.ajax({
            type:"POST",
            url: "/product/modify",
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

/*
*
*修改数量
*/
function modifyNumber()
{
        var data={
            orderId:0,
            productId:$("#productId").val(),
            number:$("#predictedNumber").val(),
            type:$('input[name="inout"]:checked').val(),
            changeNumber:$("#modifyNumber").val()
        };
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
                    $('#productNumber').val($("#predictedNumber").val());
                    $('#modifyNumber').val(1);
                    $("#jqGrid").trigger('reloadGrid');
                } else {
                    swal(res.message, {
                        icon: "error",
                    });
                }
            }
        });
}

/*
*
* 下架货物
 */
function offShelf() {
    var ids = getSelectedRows();
    if(ids==null||ids.length==0)
    {
        swal("操作失败", {
            icon: "error",
            text: "请选择您要操作的数据！！！"
        });
    }
    else
    {
        swal({
            title: "确认弹框",
            text: "确认下架Id为【'" + ids + "'】的货物吗?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/product/delete",
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
                })
            }
        });
    }
}