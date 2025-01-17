package com.imooc.li.car.dao;

import com.imooc.li.car.data.UserPasswordDO;

public interface UserPasswordDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Wed Dec 11 11:46:57 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    int deleteByUserID(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Wed Dec 11 11:46:57 CST 2019
     */
    int insert(UserPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Wed Dec 11 11:46:57 CST 2019
     */
    int insertSelective(UserPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Wed Dec 11 11:46:57 CST 2019
     */
    UserPasswordDO selectByPrimaryKey(Integer id);

    UserPasswordDO selectByUserId(int userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Wed Dec 11 11:46:57 CST 2019
     */
    int updateByPrimaryKeySelective(UserPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Wed Dec 11 11:46:57 CST 2019
     */
    int updateByPrimaryKey(UserPasswordDO record);
}