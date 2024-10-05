package com.qifei.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.qifei.common.CommonFuction;
import com.qifei.common.Constants;
import com.qifei.common.Identify;
import com.qifei.pojo.User;
import com.qifei.service.AdminService;
import com.qifei.service.PurchaserService;
import com.qifei.service.SupplierService;
import com.qifei.util.ModifyPwdRequest;
import com.qifei.util.Result;
import com.qifei.util.ResultGenerator;
import com.qifei.util.mallUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Controller
public class CommonController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PurchaserService purchaserService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private   DefaultKaptcha captchaProducer;

/*    页面跳转*/
    @GetMapping("/common/modify")
    public String modify(HttpServletRequest request) {
        request.setAttribute("path", "modify");
        User user=(User) request.getSession().getAttribute("User");

        if(user.getIdentify().equals("管理员"))
        return "qifei/adminPersonInfo";

        if(user.getIdentify().equals("采购员"))
        return "qifei/purchaserPersonInfo";

        return "qifei/supplierPersonInfo";
    }

    @GetMapping("")
    public String login() {
       return "qifei/login";
    }

    @GetMapping("/common/adminIndex")
    public String adminIndex(HttpServletRequest request) {
        request.setAttribute("path", "index");
        return "qifei/adminIndex";
    }
    @GetMapping("/common/supplierIndex")
    public String supplierIndex(HttpServletRequest request) {
        request.setAttribute("path", "index");
        return "qifei/supplierIndex";
    }
    @GetMapping("/common/purchaserIndex")
    public String puechaserIndex(HttpServletRequest request) {
        request.setAttribute("path", "index");
        return "qifei/purchaserIndex";
    }

    /*登录*/
    @PostMapping( "/common/login")
    public String login(
                        @RequestParam("identify") String identify,
                        @RequestParam("userId") Integer userId,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session) {

        if (StringUtils.isEmpty(verifyCode)) {
            session.setAttribute("errorMsg", "验证码不能为空");
            return "qifei/login";
        }
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(password)) {
            session.setAttribute("errorMsg", "用户名或密码不能为空");
            return "qifei/login";
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            session.setAttribute("errorMsg", "验证码错误");
            return "qifei/login";
        }
        User user=null;
        if(identify.equals(Identify.getADMIN()))
        {
            user= CommonFuction.transformUser(adminService.findByNamePwd(userId,password));
            if(user!=null)
            {
                session.setAttribute("User",user);
                return "redirect:/common/adminIndex";
            }
        }
        if(identify.equals(Identify.getSUPPLIER()))
        {
            user=CommonFuction.transformUser(supplierService.findBynamePwd(userId,password));
            if(user!=null) {
                session.setAttribute("User", user);
                return "redirect:/common/supplierIndex";
            }
        }

        if(identify.equals(Identify.getPURCHASER())){
            user=CommonFuction.transformUser(purchaserService.findByNamePwd(userId,password));
            if(user!=null) {
                session.setAttribute("User", user);
                return "redirect:/common/purchaserIndex";
            }
        }
            session.setAttribute("errorMsg", "登陆失败，用户不存在或密码错误");
            return "qifei/login";
    }

    @PostMapping("/common/modifyUser")
    @ResponseBody
    public Result modify(@RequestBody User user,HttpSession session)
    {
        if(Objects.isNull(user))
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);

        if(user.getIdentify().equals(Identify.getADMIN())&& adminService.modifyAdmin(user))
        {
            session.setAttribute("User",user);
            return ResultGenerator.genSuccessResult(Constants.MODIFY_SUCCESS);
        }


        if(user.getIdentify().equals(Identify.getSUPPLIER())&& supplierService.modifySupplier(user))
        {
            session.setAttribute("User",user);
            return ResultGenerator.genSuccessResult(Constants.MODIFY_SUCCESS);
        }

        if(user.getIdentify().equals(Identify.getPURCHASER())&& purchaserService.modifyPurchaser(user))
        {
            session.setAttribute("User",user);
            return ResultGenerator.genSuccessResult(Constants.MODIFY_SUCCESS);
        }
        return ResultGenerator.genFailResult(Constants.MODIFY_FAILURE);
    }

    @PostMapping ("/common/modifyPwd")
    @ResponseBody
        public Result modifyPwd(@RequestBody ModifyPwdRequest modifyPwdRequest)
    {
        if(Objects.isNull(modifyPwdRequest.getNewPwd())||Objects.isNull(modifyPwdRequest.getConfirmPwd()))
            return ResultGenerator.genFailResult(Constants.OTHER_FAILURE);
        if(!modifyPwdRequest.getNewPwd().equals(modifyPwdRequest.getConfirmPwd()))
            return ResultGenerator.genFailResult(Constants.PASSWORD_INCONSISTENT);

        if(modifyPwdRequest.getIdentify().equals(Identify.getADMIN())&& adminService.modifyPassword(modifyPwdRequest.getUid(), modifyPwdRequest.getNewPwd()))
            return ResultGenerator.genSuccessResult(Constants.MODIFY_SUCCESS);
        if(modifyPwdRequest.getIdentify().equals(Identify.getSUPPLIER())&& supplierService.modifyPassword(modifyPwdRequest.getUid(), modifyPwdRequest.getNewPwd()))
            return ResultGenerator.genSuccessResult(Constants.MODIFY_SUCCESS);
        if(modifyPwdRequest.getIdentify().equals(Identify.getPURCHASER())&& purchaserService.modifyPassword(modifyPwdRequest.getUid(), modifyPwdRequest.getNewPwd()))
            return ResultGenerator.genSuccessResult(Constants.MODIFY_SUCCESS);
        return ResultGenerator.genFailResult(Constants.MODIFY_FAILURE);
    }

    @GetMapping("/common/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("User");
        request.getSession().removeAttribute("person");
        request.getSession().removeAttribute("errorMsg");
        return "qifei/login";
    }

    @GetMapping("/common/kaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        byte[] captchaOutputStream = null;
        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String verifyCode = captchaProducer.createText();
            httpServletRequest.getSession().setAttribute("verifyCode", verifyCode);
            BufferedImage challenge = captchaProducer.createImage(verifyCode);
            ImageIO.write(challenge, "jpg", imgOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        captchaOutputStream = imgOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    @PostMapping("common/upload/file")
    @ResponseBody
    public Result upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file) throws URISyntaxException {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        System.out.println("suffixName");
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        File fileDirectory = new File(Constants.FILE_UPLOAD_DIC);

        //创建文件
        File destFile = new File(Constants.FILE_UPLOAD_DIC + newFileName);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            file.transferTo(destFile);
            Result resultSuccess = ResultGenerator.genSuccessResult();
            resultSuccess.setData(mallUtils.getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/upload/" + newFileName);
            System.out.println(resultSuccess.getData());
            return resultSuccess;
        } catch (IOException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("文件上传失败");
        }
    }
}
