package com.imooc.li.car.service;

import com.imooc.li.car.data.AdminDO;
import com.imooc.li.car.error.BusinessException;

/**
 * @author LI
 */
public interface AdminService {
    /**
     * 管理员注册功能
     *
     * @param adminDO 管理员信息
     * @throws BusinessException 异常
     */
    void insert(AdminDO adminDO) throws BusinessException;

    /**
     * 管理员信息修改功能
     *
     * @param adminDO 需要修改的信息
     * @throws BusinessException 异常
     */
    void update(AdminDO adminDO) throws BusinessException;

    /**
     * 删除管理员功能
     *
     * @param id 管理员的ID
     * @throws BusinessException 异常
     */
    void deleteByPrimaryKey(Integer id) throws BusinessException;

    /**
     * 管理员登陆功能
     *
     * @param name     管理员名称
     * @param password 管理员密码
     * @return 管理员数据
     * @throws BusinessException 异常
     */
    AdminDO login(String name, String password) throws BusinessException;
}
