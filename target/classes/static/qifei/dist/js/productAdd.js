function add()
{
    var data= {
        name: $("#name").val(),
        image:$("#carouselImg")[0].src,
        price:$("#price").val(),
        supplierId:$("#supplierId").val()
        };
    $.ajax({
        type: "POST",
        url: "/product/add",
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