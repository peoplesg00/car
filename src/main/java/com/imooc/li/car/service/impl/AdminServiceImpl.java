package com.imooc.li.car.service.impl;

import com.imooc.li.car.dao.AdminDOMapper;
import com.imooc.li.car.data.AdminDO;
import com.imooc.li.car.error.BusinessException;
import com.imooc.li.car.error.EmBusinessError;
import com.imooc.li.car.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author LI
 */
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminDOMapper adminDOMapper;

    /**
     * 构造注入
     *
     * @param adminDOMapper 管理员Mapper
     */
    @Autowired
    public AdminServiceImpl(AdminDOMapper adminDOMapper) {
        this.adminDOMapper = adminDOMapper;
    }

    @Override
    public void insert(AdminDO adminDO) {

    }

    @Override
    public void update(AdminDO adminDO) {

    }

    @Override
    public void deleteByPrimaryKey(Integer id) {

    }

    @Override
    public AdminDO login(String name, String password) throws BusinessException {
        if (name.isEmpty() || password.isEmpty()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        AdminDO adminDO = adminDOMapper.login(name);
        if (!Objects.equals(name.trim(), adminDO.getAdminName().trim()) || !Objects.equals(password.trim(), adminDO.getAdminPassword().trim())) {
            throw new BusinessException(EmBusinessError.LOGIN_FAIL);
        }
        return adminDO;
    }
}
