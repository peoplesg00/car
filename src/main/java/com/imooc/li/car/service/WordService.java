package com.imooc.li.car.service;

import com.imooc.li.car.data.WordDo;
import com.imooc.li.car.error.BusinessException;

import java.util.List;

/**
 * @author LI
 */
public interface WordService {
    /**
     * 通过用户名查询留言
     *
     * @param username 用户名
     * @return 留言信息
     * @throws BusinessException 异常
     */
    List<WordDo> selectByUserName(String username) throws BusinessException;

    /**
     * 新增留言
     *
     * @param wordDo 留言信息
     * @throws BusinessException 异常
     */
    void insert(WordDo wordDo) throws BusinessException;

    /**
     * 通过ID删除留言
     *
     * @param id ID
     * @throws BusinessException 异常
     */
    void deleteByPrimaryKey(Integer id) throws BusinessException;

    /**
     * 通过ID删除留言
     *
     * @param id ID
     * @throws BusinessException 异常
     */
    void deleteByID(Integer id) throws BusinessException;

    /**
     * 通过ID更改留言信息
     *
     * @param wordDo 更改的留言信息
     * @throws BusinessException 异常
     */
    void updateByPrimaryKeySelective(WordDo wordDo) throws BusinessException;

    /**
     * 通过问题ID查询留言
     *
     * @param id 问题ID
     * @return 留言信息
     * @throws BusinessException 异常
     */
    List<WordDo> selectByQuestionID(Integer id) throws BusinessException;
}
