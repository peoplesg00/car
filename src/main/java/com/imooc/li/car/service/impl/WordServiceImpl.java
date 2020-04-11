package com.imooc.li.car.service.impl;

import com.imooc.li.car.dao.WordDoMapper;
import com.imooc.li.car.data.WordDo;
import com.imooc.li.car.error.BusinessException;
import com.imooc.li.car.error.EmBusinessError;
import com.imooc.li.car.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LI
 */
@Service
public class WordServiceImpl implements WordService {

    private final WordDoMapper wordDoMapper;

    @Autowired
    public WordServiceImpl(WordDoMapper wordDoMapper) {
        this.wordDoMapper = wordDoMapper;
    }

    @Override
    public List<WordDo> selectByUserName(String name) throws BusinessException {
        List<WordDo> wordDoList;
        if (name.isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        try {
            wordDoList = wordDoMapper.selectByUserName(name);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        return wordDoList;
    }

    @Override
    public void insert(WordDo wordDo) throws BusinessException {
        if (wordDo == null) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        try {
            wordDoMapper.insertSelective(wordDo);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EmBusinessError.INSERT);
        }
    }

    @Override
    public void deleteByPrimaryKey(Integer id) throws BusinessException {
        if (String.valueOf(id).isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        wordDoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByID(Integer id) throws BusinessException {
        if (String.valueOf(id).isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        wordDoMapper.deleteByID(id);
    }

    @Override
    public void updateByPrimaryKeySelective(WordDo wordDo) throws BusinessException {
        if (wordDo == null) {
            throw new BusinessException(EmBusinessError.UPDATE);
        }
        wordDoMapper.updateByPrimaryKeySelective(wordDo);
    }

    @Override
    public List<WordDo> selectByQuestionID(Integer id) throws BusinessException {
        if (String.valueOf(id).trim().isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        List<WordDo> wordDoList = wordDoMapper.selectByQuestionID(id);
        return wordDoList;
    }
}
