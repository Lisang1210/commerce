package com.qifei.util;

import lombok.Data;

@Data
public class ModifyPwdRequest {
    private Integer uid;
    private String identify;
    private String newPwd;
    private String confirmPwd;
}
