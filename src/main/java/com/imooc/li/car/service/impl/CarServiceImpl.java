package com.imooc.li.car.service.impl;

import com.imooc.li.car.dao.CarDoMapper;
import com.imooc.li.car.data.CarDo;
import com.imooc.li.car.error.BusinessException;
import com.imooc.li.car.error.EmBusinessError;
import com.imooc.li.car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LI
 */
@Service
public class CarServiceImpl implements CarService {

    private final CarDoMapper carDoMapper;

    @Autowired
    public CarServiceImpl(CarDoMapper carDoMapper) {
        this.carDoMapper = carDoMapper;
    }

    @Override
    public void insert(CarDo carDo) throws BusinessException {
        if (carDo == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        try {
            carDoMapper.insertSelective(carDo);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "注册失败");
        }
    }

    /**
     * 修改用户帖子信息接口
     *
     * @param carDo 传过来的数据
     * @throws BusinessException 异常
     */
    @Override
    public void update(CarDo carDo) throws BusinessException {
        if (carDo == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "修改失败");
        }
        carDoMapper.updateByPrimaryKeySelective(carDo);
        return;
    }

    /**
     * 通过用户帖子ID删除
     *
     * @param id 用户帖子ID
     * @throws BusinessException
     */
    @Override
    public void deleteByPrimaryKey(Integer id) throws BusinessException {
        try {
            carDoMapper.deleteByPrimaryKey(id);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "删除失败");
        }
        return;
    }

    /**
     * 管理员删除
     *
     * @param id
     * @throws BusinessException
     */
    @Override
    public void deleteByID(Integer id) throws BusinessException {
        if (String.valueOf(id).isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        carDoMapper.deleteByID(id);
        return;
    }

    /**
     * 获取用户帖子接口实现
     *
     * @return carDoList 用户帖子数据
     * @throws BusinessException
     */
    @Override
    public List<CarDo> selectALL() throws BusinessException {
        List<CarDo> carDoList = carDoMapper.selectALL();
        if (carDoList == null) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        return carDoList;
    }

    /**
     * 通过用户名获取帖子的实现
     *
     * @param name 用户名
     * @return carDoList 用户帖子数据
     * @throws BusinessException 异常
     */
    @Override
    public List<CarDo> selectByUserName(String name) throws BusinessException {
        if (name == null) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        List<CarDo> carDoList = carDoMapper.selectByUserName(name);
        if (carDoList == null) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        return carDoList;
    }

    /**
     * 通过用户名获取帖子的实现
     *
     * @param id 用户名
     * @return carDoList 用户帖子数据
     * @throws BusinessException 异常
     */
    @Override
    public List<CarDo> selectByUserID(Integer id) throws BusinessException {
        if (id == null) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        List<CarDo> carDoList = carDoMapper.selectByUserID(id);
        if (carDoList == null) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        return carDoList;
    }

    /**
     * 指定从哪开始，指定需要几行
     *
     * @param offset 启始位置
     * @param limit  指定行数
     * @return 帖子信息
     * @throws BusinessException 异常
     */
    @Override
    public List<CarDo> queryAllByLimit(int offset, int limit) throws BusinessException {
        if (String.valueOf(offset).isEmpty() || String.valueOf(limit).isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        List<CarDo> carDoList = carDoMapper.queryAllByLimit(offset, limit);
        if (carDoList.isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        return carDoList;
    }
}
