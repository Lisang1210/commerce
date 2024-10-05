$(function (){
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
                $("#carouselImg").attr("src", r.data);
                $("#carouselImg").attr("style", "width: 128px;height: 128px;display:block;");
                return false;
            } else {
                alert("error");
            }
        }
    });
});


function modifyUser()
{
        var id = $("#id").val();
        var name = $("#name").val();
        var contact = $("#contact").val();
        var image = $("#carouselImg").attr('src');
        var identify = $("#identify").val();

    var phonePattern = /^1(3[0-9]|5[0-3,5-9]|7[1-3,5-8]|8[0-9])\d{8}$/;  // 这里可以根据需要调整正则表达式以匹配不同的电话号码格式

    if (!phonePattern.test(contact)) {
        alert("请输入的有效电话号码");
    }
    else
    {

        if (identify == "管理员") identify = "0";
        if (identify == "供应商") identify = "1";
        if (identify == "采购员") identify = "2";
        console.log(image)
        var data = {
            id: id,
            name: name,
            contact: contact,
            image: image,
            identify: identify
        };
        $.ajax({
            type: "POST",
            url: "/common/modifyUser",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (res) {
                if (res.resultCode == 200) {
                    swal(res.message, {
                        icon: "success",
                    });
                } else {
                    swal(res.message, {
                        icon: "error",
                    });
                }
            }
        })
    }

}

function passwordShow()
{
    $('#passwordModal').modal('show');
}

function modifyPassword()
{
    var uid=$("#id").val();
    var newPwd=$('#newPwd').val();
    var confirmPwd=$('#confirmPwd').val();
    var identify=$("#identify").val();
    if(identify=="管理员") identify="0";
    if(identify=="供应商") identify="1";
    if(identify=="采购员") identify="2";
    var data={
        uid:uid,
        identify:identify,
        newPwd:newPwd,
        confirmPwd:confirmPwd
    };
    $.ajax({
        type:"POST",
        url: "/common/modifyPwd",
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
    })
}