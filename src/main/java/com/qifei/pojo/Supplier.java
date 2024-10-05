package com.qifei.pojo;

import lombok.Data;

@Data
public class Supplier {
    private Integer id;
    private String name;
    private String password;
    private String image;
    private String contact;
    private Integer adminId;
}
