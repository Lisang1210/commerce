$(function () {
    $("#jqGrid").jqGrid({
        url: '/purchaser/list',
        datatype: "json",
        colModel: [
            {label: '编号', name: 'id', index: 'id', width: 60, key: true},
            {label: '名称', name: 'name', index: 'name', width: 120},
            {label: '头像', name: 'image', index: 'image', width: 120, formatter: coverImageFormatter},
            {label: '联系方式', name: 'contact', index: 'contact', width: 120},
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
        return $("#searchPurchaser").val(); // 正确的 ID 选择器
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

    function coverImageFormatter(cellvalue) {
        return "<img src='" + cellvalue + "' height=\"80\" width=\"80\" alt='供应商头像'/>";
    }

    new AjaxUpload('#uploadCarouselImage', {
        action: '/common/upload/file',
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                alert('只支持jpg、png、gif格式的文件！');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r != null && r.resultCode == 200) {
                console.log("carousel:"+r.data);
                $("#carouselImg").attr("src", r.data);
                $("#carouselImg").attr("style", "width: 128px;height: 128px;display:block;");
                return false;
            } else {
                alert("error");
            }
        }
    });
});

/**
 * 添加
 */

/*
*
* 添加框设置值
 */
function reset() {
    $("#name").val("");
    $("#password").val("");
    $("#contact").val("");
    $("#carouselImg").attr("src", '/qifei/dist/img/img-upload.png');
    $("#carouselImg").attr("style", "height: 64px;width: 64px;display:block;");
    $('#edit-error-msg').css("display", "none");
}

/*
*
* 展示添加框
* */
function addShow()
{
    $('#addModal').modal('show');
}


/*
*
* 添加
* */
function add() {

    var adminId=$("#adminId").val();
    var name=$("#name").val();
    var contact=$("#contact").val();
    var password=$("#password").val();
    var image=$("#carouselImg").attr('src');

    console.log("name="+name);
    console.log("contact="+contact);
    console.log("password="+password);

    var phonePattern = /^1(3[0-9]|5[0-3,5-9]|7[1-3,5-8]|8[0-9])\d{8}$/;  // 这里可以根据需要调整正则表达式以匹配不同的电话号码格式

    if(name==null||contact==null||password||name==""||contact==""||password=="")
    {
        swal("操作失败", {
            icon: "warning",
            text: "采购员名称、联系方式、密码不能为空！！！"
        });
    }
    else  if (!phonePattern.test(contact)) {
        alert("请输入的有效电话号码");
    }
    else
    {
        console.log("purchaser_AdminId="+adminId);

        var data={
            adminId:adminId,
            name:name,
            contact:contact,
            password:password,
            image:image
        };
        $.ajax({
            type:"POST",
            url: "/purchaser/add",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (res) {
                if (res.resultCode == 200) {
                    swal(res.message, {
                        icon: "success",
                    });
                    $("#jqGrid").trigger('reloadGrid');
                    reset();
                } else {
                    swal(res.message, {
                        icon: "error",
                    });
                }
            }
        })

    }
}

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