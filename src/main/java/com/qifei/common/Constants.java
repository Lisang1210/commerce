package com.qifei.common;

public class Constants {

    //上传文件的默认url前缀，根据部署设置自行修改
    public final static String FILE_UPLOAD_DIC = "D:\\upload\\";

    //每页展示的条数
    public static final Integer PAGE=1;
    public static final Integer PAGE_SIZE=10;

    //操作成功或失败的错误提示
    public static final String DELETE_SUCCESS="删除成功";
    public static final String DELETE_FAILURE="删除失败";

    public static final String ADD_SUCCESS="添加成功";
    public static final String ADD_FAILURE="添加失败";

    public static final String MODIFY_SUCCESS="修改成功";
    public static final String MODIFY_FAILURE="修改失败";

    public static final String PASSWORD_INCONSISTENT="两次密码不一致";

    public static final String HANDLE_SUCCESS="处理完成";
    public static final String HANDLE_FAILURE="处理失败";

    public static final String OFFSHELF_SUCCESS="下架成功";
    public static final String OFFSHELF_FAILURE="下架失败";

    //其他错误（统一回复）
    public static final String OTHER_FAILURE="参数异常";

    //出入库类型
    public static final String IN_Storage="1";
    public static final String OUT_Storage="0";


    //订单状态
    public static final String ORDER_REJECT="0";
    public static final String ORDER_FINISH="2";
    public static final String ORDER_AWAIT="1";
}
