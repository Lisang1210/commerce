package com.qifei.controller;

import com.qifei.common.Constants;
import com.qifei.pojo.User;
import com.qifei.service.RecordService;
import com.qifei.util.ProductData;
import com.qifei.util.Result;
import com.qifei.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @GetMapping("/list")
    @ResponseBody
    public Result list(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam(defaultValue = "") String name, HttpSession session) {
        if (Objects.isNull(page) || Objects.isNull(limit) || Objects.isNull(session.getAttribute("type"))) {
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        }

        System.out.println("recordType=" + session.getAttribute("type"));

        User user = (User) session.getAttribute("User");

        System.out.println("recordSupplierId=" + user.getId());

        return ResultGenerator.genSuccessResult(recordService.recordList(session.getAttribute("type").toString(), page, limit, name, user.getId()));
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] params)
    {
        if (StringUtils.isEmpty(params)|| Objects.isNull(params)) {
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        }
        List<Integer> list= Arrays.asList(params);
        if(recordService.deleteRecords(list))
            return ResultGenerator.genSuccessResult(Constants.DELETE_SUCCESS);
        return ResultGenerator.genFailResult(Constants.ADD_FAILURE);
    }


    @RequestMapping(value = "/productData", method = RequestMethod.GET)
    @ResponseBody
    public ProductData orderData(HttpSession session) {
        User user=(User) session.getAttribute("User");
        return recordService.getProductData(user.getId(), user.getIdentify());
    }
}
