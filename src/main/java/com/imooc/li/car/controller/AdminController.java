package com.imooc.li.car.controller;

import com.imooc.li.car.error.BusinessException;
import com.imooc.li.car.error.EmBusinessError;
import com.imooc.li.car.response.CommonReturnType;
import com.imooc.li.car.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author LI
 */
@Controller
@RequestMapping("/admin")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestParam(value = "name", required = false) String name,
                        @RequestParam(value = "password", required = false) String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (name.isEmpty() || password.isEmpty()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        adminService.login(name, EncodeByMd5(password));
        return CommonReturnType.create(null);
    }

    /**
     * 加密密码
     */
    private String EncodeByMd5(String string) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(string.getBytes("utf-8"));
    }
}
