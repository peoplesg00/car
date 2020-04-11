package com.imooc.li.car.service;

import com.imooc.li.car.data.CarDo;
import com.imooc.li.car.error.BusinessException;

import java.util.List;

/**
 * @author LI
 */
public interface CarService {
    /**
     * 用户创建帖子功能
     *
     * @param carDo 帖子信息
     * @throws BusinessException 异常
     */
    void insert(CarDo carDo) throws BusinessException;

    /**
     * 用户更新帖子信息
     *
     * @param carDo 帖子信息
     * @throws BusinessException 异常
     */
    void update(CarDo carDo) throws BusinessException;

    /**
     * 用户通过ID删除帖子
     *
     * @param id 帖子ID
     * @throws BusinessException 异常
     */
    void deleteByPrimaryKey(Integer id) throws BusinessException;

    /**
     * 管理员通过ID删除帖子
     *
     * @param id 帖子ID
     * @throws BusinessException 异常
     */
    void deleteByID(Integer id) throws BusinessException;

    /**
     * 查询所有帖子信息的功能
     *
     * @return 帖子信息
     * @throws BusinessException 异常
     */
    List<CarDo> selectALL() throws BusinessException;

    /**
     * 通过用户名查询帖子信息
     *
     * @param name 用户名
     * @return 帖子信息
     * @throws BusinessException 异常
     */
    List<CarDo> selectByUserName(String name) throws BusinessException;

    /**
     * 通过用户名获取帖子的实现
     *
     * @param id 用户名
     * @return carDoList 用户帖子数据
     * @throws BusinessException 异常
     */
    List<CarDo> selectByUserID(Integer id) throws BusinessException;

    /**
     * 指定从哪开始，指定需要几行
     *
     * @param offset 启始位置
     * @param limit  指定行数
     * @return 帖子信息
     * @throws BusinessException 异常
     */
    List<CarDo> queryAllByLimit(int offset, int limit) throws BusinessException;
}
