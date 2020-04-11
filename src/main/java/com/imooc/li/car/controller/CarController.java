package com.imooc.li.car.controller;

import com.imooc.li.car.data.CarDo;
import com.imooc.li.car.error.BusinessException;
import com.imooc.li.car.error.EmBusinessError;
import com.imooc.li.car.response.CommonReturnType;
import com.imooc.li.car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author LI
 */
@Controller
@RequestMapping("/car")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     * 用户插入帖子接口
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Object insert(@RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "user_name", required = false) String username,
                         @RequestParam(value = "power", required = false) String power,
                         @RequestParam(value = "price", required = false) String price,
                         @RequestParam(value = "date") String date,
                         @RequestParam(value = "description", required = false) String description,
                         @RequestParam(value = "file", required = false) MultipartFile multipartFile) throws BusinessException, IOException, ParseException {
        if (Objects.requireNonNull(multipartFile.getOriginalFilename()).isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        String path = "D:\\java程序设计\\car\\src\\main\\resources\\picture\\car\\User_Car\\" + multipartFile.getOriginalFilename();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        //将从前台获取的时间字符串序列化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = simpleDateFormat.parse(date);
        CarDo carDo = new CarDo();
        carDo.setName(name);
        carDo.setPower(power);
        carDo.setPrice(price);
        carDo.setDate(date1);
        carDo.setUsername(username);
        carDo.setDescription(description);
        carDo.setVehicle(multipartFile.getOriginalFilename());
        carService.insert(carDo);
        multipartFile.transferTo(file);
        System.out.println(carDo.getDate());
        return CommonReturnType.create(null);
    }

    /**
     * 获取用户帖子接口
     */
    @RequestMapping(value = "/selectALL", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType selectALL() throws BusinessException {
        List<CarDo> carDoList = carService.selectALL();
        if (carDoList.isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        return (CommonReturnType) CommonReturnType.create(carDoList);
    }

    /**
     * 通过用户名获取帖子接口
     *
     * @param name 用户名
     * @return carDoList 帖子信息
     * @throws BusinessException 异常
     */
    @RequestMapping(value = "selectByUserName", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType selectByUserName(@RequestParam(value = "name", required = false) String name) throws BusinessException {
        if (name.isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        List<CarDo> carDoList = carService.selectByUserName(name);
        if (carDoList.isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        return (CommonReturnType) CommonReturnType.create(carDoList);
    }

    /**
     * 修改用户帖子信息
     */
    @RequestMapping(value = "/updateToCar", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@RequestParam(value = "id", required = false) Integer id,
                         @RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "user_name", required = false) String username,
                         @RequestParam(value = "power", required = false) String power,
                         @RequestParam(value = "price", required = false) String price,
                         @RequestParam(value = "date") String date,
                         @RequestParam(value = "description", required = false) String description,
                         @RequestParam(value = "img", required = false) MultipartFile multipartFile) throws BusinessException, IOException, ParseException {
        if (Objects.requireNonNull(multipartFile.getOriginalFilename()).isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        String path = "D:\\java程序设计\\car\\src\\main\\resources\\picture\\car\\User_Car\\" + multipartFile.getOriginalFilename();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        //将从前台获取的时间字符串序列化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date1 = simpleDateFormat.parse(date);
        CarDo carDo = new CarDo();
        carDo.setId(id);
        carDo.setName(name);
        carDo.setPower(power);
        carDo.setPrice(price);
        carDo.setDate(date1);
        carDo.setUsername(username);
        carDo.setDescription(description);
        carDo.setVehicle(multipartFile.getOriginalFilename());
        carService.update(carDo);
        multipartFile.transferTo(file);
        System.out.println(carDo.getDate());
        return CommonReturnType.create(null);
    }

    /**
     * 通过ID删除帖子
     */
    @RequestMapping(value = "/deleteByPrimaryKey", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteByPrimaryKey(@RequestParam(value = "id", required = false) Integer id) throws BusinessException {
        carService.deleteByPrimaryKey(id);
        return CommonReturnType.create(null);
    }

    /**
     * 通过ID获取帖子
     */
    @RequestMapping(value = "/selectByUserID", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType selectByUserID(@RequestParam(value = "id", required = false) Integer id) throws BusinessException {
        if (String.valueOf(id).trim().isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        List<CarDo> carDoList = carService.selectByUserID(id);
        if (carDoList.isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        return (CommonReturnType) CommonReturnType.create(carDoList);
    }

    /**
     * 通过具体行数来查询帖子
     */
    @RequestMapping(value = "/queryAllByLimit", method = RequestMethod.POST)
    @ResponseBody
    public Object queryAllByLimit(@RequestParam(value = "offset", required = false) int offset,
                                  @RequestParam(value = "limit", required = false) int limit) throws BusinessException {
        if (String.valueOf(offset).isEmpty() || String.valueOf(limit).isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        List<CarDo> carDoList = carService.queryAllByLimit(offset, limit);
        return CommonReturnType.create(carDoList);
    }

    /**
     * 通过具体的ID删除
     */
    @RequestMapping(value = "/deleteByID", method = RequestMethod.GET)
    @ResponseBody
    public void deleteByID(@RequestParam(value = "id", required = false) Integer id,
                           HttpServletRequest request,
                           HttpServletResponse response) throws BusinessException, ServletException, IOException {
        if (String.valueOf(id).isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        carService.deleteByID(id);
        request.getRequestDispatcher("../html/admin.html").forward(request, response);
    }
}
