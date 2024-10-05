package com.qifei.pojo;

import lombok.Data;

@Data
public class Purchaser {
    private Integer id;
    private String name;
    private String password;
    private String image;
    private String contact;
    private Integer adminId;
}
