package com.imooc.li.car.controller;

import com.imooc.li.car.data.UserDO;
import com.imooc.li.car.error.BusinessException;
import com.imooc.li.car.error.EmBusinessError;
import com.imooc.li.car.response.CommonReturnType;
import com.imooc.li.car.service.UserService;
import com.imooc.li.car.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

/**
 * @author LI
 */
@Controller
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * , consumes = "multipart/form-data"
     * 注册接口
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Object register(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "password", required = false) String password,
                           @RequestParam(value = "phone", required = false) String phone,
                           @RequestParam(value = "location", required = false) String location,
                           @RequestParam(value = "file", required = false) MultipartFile multipartFile,
                           @RequestParam(value = "secret", required = false) String secret) throws IOException, NoSuchAlgorithmException, BusinessException {
        List<UserDO> userDOList = userService.selectALL();
        boolean flag = ListToEquals(userDOList, name);
        if (!flag) {
            return CommonReturnType.create("用户名重复");
        }
        if (Objects.requireNonNull(multipartFile.getOriginalFilename()).isEmpty()) {
            return CommonReturnType.create("找不到文件");
        }
        String path = "D:\\java程序设计\\car\\src\\main\\resources\\picture\\user\\" + multipartFile.getOriginalFilename();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setImg(multipartFile.getOriginalFilename());
        userModel.setLocation(location);
        userModel.setSecret(this.encodebymd5(secret));
        userModel.setPhone(phone);
        userModel.setPassword(this.encodebymd5(password));
        userService.register(userModel);
        multipartFile.transferTo(file);
        return CommonReturnType.create(null);
    }

    /**
     * 登陆接口
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestParam(value = "name", required = false) String name,
                        @RequestParam(value = "password", required = false) String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, BusinessException {
        userService.login(name, this.encodebymd5(password));
        return CommonReturnType.create(null);
    }

    /**
     * 获取信息接口
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType list() throws BusinessException {
        List<UserDO> userDOList = userService.selectALL();
        if (userDOList == null) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        return (CommonReturnType) CommonReturnType.create(userDOList);
    }

    /**
     * 通过用户名获取信息
     */
    @RequestMapping(value = "/selectByUserName", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType selectByUserName(@RequestParam(value = "name", required = false) String name) throws BusinessException {
        List<UserDO> userDOList = userService.selectByUserName(name);
        if (userDOList.isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        return (CommonReturnType) CommonReturnType.create(userDOList);
    }

    /**
     * 通过密保修改用户密码
     */
    @RequestMapping(value = "/updateByUserSecret", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType updateByUserSecret(@RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "secret", required = false) String secret,
                                               @RequestParam(value = "password", required = false) String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        if (name.isEmpty() || secret.isEmpty() || password.isEmpty()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        } else {
            userService.updateByUserSecret(name, encodebymd5(secret), encodebymd5(password));
        }
        return (CommonReturnType) CommonReturnType.create(null);
    }

    /**
     * 指定行数来获取数据
     */
    @RequestMapping(value = "/queryAllByLimit", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType queryAllByLimit(@RequestParam(value = "offset", required = false) int offset,
                                            @RequestParam(value = "limit", required = false) int limit) throws BusinessException {
        if (String.valueOf(offset).isEmpty() || String.valueOf(limit).isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        List<UserDO> userDOList = userService.queryAllByLimit(offset, limit);
        return (CommonReturnType) CommonReturnType.create(userDOList);
    }

    /**
     * 删除数据
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public void delete(@RequestParam(value = "id", required = false) Integer id,
                       HttpServletResponse response) throws BusinessException, IOException {
        if (String.valueOf(id).isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        userService.delete(id);
        response.sendRedirect("../html/admin.html");
    }

    /**
     * 加密密码
     */
    private String encodebymd5(String string) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        BASE64Encoder encoder = new BASE64Encoder();
        String str = encoder.encode(string.getBytes("utf-8"));
        return str;
    }

    /**
     * 解密密文
     */
    public String decoderbybase(String s) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        String s1 = new String(decoder.decodeBuffer(s), "utf-8");
        System.out.println(new String(decoder.decodeBuffer(s), "utf-8"));
        return s1;
    }

    /**
     * 判断用户名称是否重复
     */
    private boolean ListToEquals(List<UserDO> userDOList, String name) {
        for (int i = 0; i < userDOList.size(); i++) {
            if (userDOList.get(i).getName().equals(name)) {
                return false;
            }
        }
        return true;
    }
}
