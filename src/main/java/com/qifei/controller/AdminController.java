package com.qifei.controller;

import com.qifei.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/manageSupplier")
    public String manageSupplier(HttpServletRequest request)
    {
        request.setAttribute("path", "manageSupplier");
        return "qifei/manageSupplier";
    }

    @GetMapping("/managePurchaser")
    public String managePurchaser(HttpServletRequest request)
    {
        request.setAttribute("path", "managePurchaser");
        return "qifei/managePurchaser";
    }
}
