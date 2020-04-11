package com.imooc.li.car.service;

import com.imooc.li.car.data.UserDO;
import com.imooc.li.car.error.BusinessException;
import com.imooc.li.car.service.model.UserModel;

import java.util.List;

/**
 * @author LI
 */
public interface UserService {
    /**
     * 用户注册功能
     *
     * @param userModel 用户信息
     * @throws BusinessException 异常
     */
    void register(UserModel userModel) throws BusinessException;

    /**
     * 用户登录功能
     *
     * @param name     用户名
     * @param password 用户密码
     * @return 用户信息
     * @throws BusinessException 异常
     */
    UserModel login(String name, String password) throws BusinessException;

    /**
     * 查询所有用户
     *
     * @return 用户信息
     * @throws BusinessException 异常
     */
    List<UserDO> selectALL() throws BusinessException;

    /**
     * 通过用户名查询
     *
     * @param name 用户名
     * @return 用户信息
     * @throws BusinessException 异常
     */
    List<UserDO> selectByUserName(String name) throws BusinessException;

    /**
     * 通过密保修改密码
     *
     * @param name     用户名
     * @param password 更改的密码
     * @param secret   密保
     * @throws BusinessException 异常
     */
    void updateByUserSecret(String name, String password, String secret) throws BusinessException;

    /**
     * 指定起始位置与指定行数
     *
     * @param offset 起始位置
     * @param limit  指定行数
     * @return 用户信息
     * @throws BusinessException 异常
     */
    List<UserDO> queryAllByLimit(int offset, int limit) throws BusinessException;

    /**
     * 通过ID删除用户
     *
     * @param id 用户ID
     * @throws BusinessException 异常
     */
    void delete(Integer id) throws BusinessException;
}
