package com.imooc.li.car.service.impl;

import com.imooc.li.car.dao.UserDOMapper;
import com.imooc.li.car.dao.UserPasswordDOMapper;
import com.imooc.li.car.data.UserDO;
import com.imooc.li.car.data.UserPasswordDO;
import com.imooc.li.car.error.BusinessException;
import com.imooc.li.car.error.EmBusinessError;
import com.imooc.li.car.service.UserService;
import com.imooc.li.car.service.model.UserModel;
import com.imooc.li.car.validator.ValidationResult;
import com.imooc.li.car.validator.validatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author LI
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserDOMapper userDOMapper;

    private final UserPasswordDOMapper userPasswordDOMapper;

    private final validatorImpl validator;

    @Autowired
    public UserServiceImpl(UserDOMapper userDOMapper, UserPasswordDOMapper userPasswordDOMapper, validatorImpl validator) {
        this.userDOMapper = userDOMapper;
        this.userPasswordDOMapper = userPasswordDOMapper;
        this.validator = validator;
    }

    /**
     * 注册接口
     *
     * @param userModel
     * @throws BusinessException
     */
    @Override
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }
        UserDO userDO = FromUserDO(userModel);
        try {
            userDOMapper.insertSelective(userDO);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "注册失败");
        }
        List<UserDO> userDOList = userDOMapper.selectByUserName(userDO.getName());
        userModel.setId(userDOList.get(userDOList.size() - 1).getId());
        UserPasswordDO userPasswordDO = FromUserPasswordDO(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
        return;
    }

    /**
     * 登陆接口
     *
     * @param name
     * @param password
     * @return
     * @throws BusinessException
     */
    @Override
    public UserModel login(String name, String password) throws BusinessException {
        UserDO userDO = userDOMapper.selectByName(name);
        if (userDO == null) {
            throw new BusinessException(EmBusinessError.LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO, userPasswordDO);
        if (!Objects.equals(name.trim(), userModel.getName().trim()) || !Objects.equals(password.trim(), userModel.getPassword().trim())) {
            throw new BusinessException(EmBusinessError.LOGIN_FAIL);
        }
        return userModel;
    }

    /**
     * 查询所有数据
     *
     * @return userDO
     * @throws BusinessException
     */
    @Override
    public List<UserDO> selectALL() throws BusinessException {
        List<UserDO> userDO = userDOMapper.selectALL();
        if (userDO == null) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        return userDO;
    }

    /**
     * 通过用户名获取信息
     *
     * @param name
     * @return userDOList
     * @throws BusinessException
     */
    @Override
    public List<UserDO> selectByUserName(String name) throws BusinessException {
        List<UserDO> userDOList = userDOMapper.selectByUserName(name);
        if (userDOList.isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        return userDOList;
    }

    /**
     * 通过密保修改密码
     *
     * @param name     用户名
     * @param secret   用户密保
     * @param password 用户密码
     * @return
     * @throws BusinessException
     */
    @Override
    public void updateByUserSecret(String name, String secret, String password) throws BusinessException {
        UserDO userDO = userDOMapper.selectByName(name);
        if (userDO == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        if (!StringUtils.equals(secret, userPasswordDO.getSecret())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        userPasswordDO.setPassword(password);
        System.out.println("UserServiceImpl.updateByUserSecret:" + userPasswordDO.getPassword());
        userPasswordDOMapper.updateByPrimaryKeySelective(userPasswordDO);
    }

    /**
     * 通过查询条数来获取数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return userDOList 用户数据
     * @throws BusinessException
     */
    @Override
    public List<UserDO> queryAllByLimit(int offset, int limit) throws BusinessException {
        if (String.valueOf(offset).isEmpty() || String.valueOf(limit).isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        List<UserDO> userDOList = userDOMapper.queryAllByLimit(offset, limit);
        System.out.println(userDOList.size());
        return userDOList;
    }

    /**
     * 删除接口
     *
     * @param id
     * @throws BusinessException
     */
    @Override
    public void delete(Integer id) throws BusinessException {
        if (String.valueOf(id).isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        userDOMapper.deleteByPrimaryKey(id);
        userPasswordDOMapper.deleteByUserID(id);
    }

    /**
     * 交换数据
     */
    private UserDO FromUserDO(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }

    /**
     * 交换数据
     */
    private UserPasswordDO FromUserPasswordDO(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setPassword(userModel.getPassword());
        userPasswordDO.setSecret(userModel.getSecret());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }

    /**
     * 整合数据
     */
    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO) {
        if (userDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        BeanUtils.copyProperties(userPasswordDO, userModel);
        userModel.setPassword(userPasswordDO.getPassword());
        return userModel;
    }
}
