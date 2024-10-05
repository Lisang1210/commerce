package com.qifei.controller;

import com.qifei.common.Constants;
import com.qifei.pojo.Purchaser;
import com.qifei.pojo.User;
import com.qifei.service.PurchaserService;
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
@RequestMapping("/purchaser")
public class PurchaserController {
    @Autowired
    private PurchaserService purchaserService;




    @GetMapping("/orderAnalyze")
    public String orderAnalyze(HttpServletRequest request)
    {
        request.setAttribute("path", "orderAnalyze");
        return "qifei/orderAnalyze";
    }

    @GetMapping("/orderAdd")
    public String addOrder(HttpServletRequest request)
    {
        request.setAttribute("path", "orderAdd");
        return "qifei/orderAdd";
    }

    @GetMapping("/orderRecord")
    public String orderRecord(HttpServletRequest request)
    {
        request.setAttribute("path", "orderRecord");
        return "qifei/orderList";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam(defaultValue = "") String name, HttpSession session) {
        if (Objects.isNull(page)||Objects.isNull(limit)) {
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        }
        return ResultGenerator.genSuccessResult(purchaserService.findByName(page, limit, name,((User)session.getAttribute("User")).getId()));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(@RequestBody Purchaser purchaser) {
        System.out.println("purchaser_AdminId:"+purchaser.getAdminId());
        if (Objects.isNull(purchaser)) {
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        }
        if(purchaserService.addPurchaser(purchaser))
            return ResultGenerator.genSuccessResult(Constants.ADD_SUCCESS);
        return ResultGenerator.genFailResult(Constants.ADD_FAILURE);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] params) {
        if (StringUtils.isEmpty(params)|| Objects.isNull(params)) {
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        }
        List<Integer> list= Arrays.asList(params);
        if(purchaserService.deletePurchasers(list))
            return ResultGenerator.genSuccessResult(Constants.DELETE_SUCCESS);
        return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
    }
}
