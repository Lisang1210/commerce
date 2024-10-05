package com.qifei.controller;

import com.qifei.common.Constants;
import com.qifei.pojo.Supplier;
import com.qifei.pojo.User;
import com.qifei.service.SupplierService;
import com.qifei.util.Result;
import com.qifei.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/dataShow")
    public String dataShow(HttpServletRequest request)
    {
        request.setAttribute("path", "dataShow");
        return "qifei/productDataShow";
    }

    @GetMapping("/productList")
    public String productRecord(HttpServletRequest request)
    {
        request.setAttribute("path", "productList");
        return "qifei/productList";
    }

    @GetMapping("/inRecord")
    public String inRecord(HttpServletRequest request)
    {
        request.setAttribute("path", "inRecord");
        request.getSession().setAttribute("type", Constants.IN_Storage);
        return "qifei/recordList";
    }

    @GetMapping("/outRecord")
    public String outRecord(HttpServletRequest request)
    {
        request.setAttribute("path", "outRecord");
        request.getSession().setAttribute("type", Constants.OUT_Storage);
        return "qifei/recordList";
    }

    @GetMapping("/handleOrder")
    public String subProductNumber(HttpServletRequest request)
    {
        request.setAttribute("path", "handleOrder");
        return "qifei/handleOrder";
    }


    //获取列表
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Integer page,@RequestParam Integer limit,@RequestParam String name,HttpSession session) {
            if (Objects.isNull(page)||Objects.isNull(limit)) {
                return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
            }
         return ResultGenerator.genSuccessResult(supplierService.findByName(page,limit,name,((User)session.getAttribute("User")).getId()));
    }

    //添加采购员
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(@RequestBody Supplier supplier) {
        System.out.println(supplier.getImage());

        if (Objects.isNull(supplier)) {
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        }

       if(supplierService.addSupplier(supplier))
           return ResultGenerator.genSuccessResult(Constants.ADD_SUCCESS);
        return ResultGenerator.genFailResult(Constants.ADD_FAILURE);
    }

    //删除采购员
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] params) {
        if (StringUtils.isEmpty(params)||Objects.isNull(params)) {
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        }
        List<Integer> list= Arrays.asList(params);
        if(supplierService.deleteSuppliers(list))
            return ResultGenerator.genSuccessResult(Constants.DELETE_SUCCESS);
        return ResultGenerator.genFailResult(Constants.DELETE_FAILURE);
    }


}
