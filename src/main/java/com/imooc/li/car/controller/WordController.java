package com.imooc.li.car.controller;

import com.imooc.li.car.data.WordDo;
import com.imooc.li.car.error.BusinessException;
import com.imooc.li.car.error.EmBusinessError;
import com.imooc.li.car.response.CommonReturnType;
import com.imooc.li.car.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author LI
 */
@Controller
@RequestMapping("/word")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class WordController {

    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    /**
     * 留言接口
     *
     * @param message    留言者
     * @param username   被留言者
     * @param questionID 问题帖子ID
     * @param problem    问题
     * @return null
     * @throws BusinessException 异常
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Object insert(@RequestParam(value = "message", required = false) String message,
                         @RequestParam(value = "username", required = false) String username,
                         @RequestParam(value = "questionID", required = false) Integer questionID,
                         @RequestParam(value = "problem", required = false) String problem) throws BusinessException {
        WordDo wordDo = new WordDo();
        wordDo.setMessage(message);
        wordDo.setUsername(username);
        wordDo.setQuestionid(questionID);
        wordDo.setProblem(problem);
        wordDo.setProgress("未完成");
        wordService.insert(wordDo);
        return CommonReturnType.create(null);
    }

    /**
     * 修改留言现状
     *
     * @param select 状态
     * @param id     ID
     * @return null
     * @throws BusinessException 异常
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@RequestParam(value = "select", required = false) String select,
                         @RequestParam(value = "ID", required = false) Integer id) throws BusinessException {
        WordDo wordDo = new WordDo();
        wordDo.setId(id);
        wordDo.setProgress(select);
        wordService.updateByPrimaryKeySelective(wordDo);
        return CommonReturnType.create(null);
    }

    /**
     * 修改给管理员的留言状态
     *
     * @param id       ID
     * @param request  request
     * @param response response
     * @throws BusinessException 异常
     * @throws ServletException  异常
     * @throws IOException       异常
     */
    @RequestMapping(value = "/updateTo", method = RequestMethod.GET)
    @ResponseBody
    public void updateTo(@RequestParam(value = "ID", required = false) Integer id,
                         HttpServletRequest request,
                         HttpServletResponse response) throws BusinessException, ServletException, IOException {
        WordDo wordDo = new WordDo();
        wordDo.setId(id);
        wordDo.setProgress("完成");
        wordService.updateByPrimaryKeySelective(wordDo);
        request.getRequestDispatcher("../html/admin.html").forward(request, response);
    }

    /**
     * 删除留言
     *
     * @param id ID
     * @return null
     * @throws BusinessException 异常
     */
    @RequestMapping(value = "/deleteByPrimaryKey", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteByPrimaryKey(@RequestParam(value = "id", required = false) Integer id) throws BusinessException {
        if (String.valueOf(id).isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        wordService.deleteByPrimaryKey(id);
        return CommonReturnType.create(null);
    }

    /**
     * 管理员删除留言
     *
     * @param id       ID
     * @param request  request
     * @param response response
     * @throws BusinessException 异常
     * @throws ServletException  异常
     * @throws IOException       异常
     */
    @RequestMapping(value = "/deleteByID", method = RequestMethod.GET)
    @ResponseBody
    public void deleteByID(@RequestParam(value = "id", required = false) Integer id,
                           HttpServletRequest request,
                           HttpServletResponse response) throws BusinessException, ServletException, IOException {
        if (String.valueOf(id).isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        wordService.deleteByPrimaryKey(id);
        request.getRequestDispatcher("../html/admin.html").forward(request, response);
    }

    /**
     * 查询留言
     *
     * @param name 用户名
     * @return wordDoList
     * @throws BusinessException 异常
     */
    @RequestMapping(value = "/selectByUserName", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType selectByUserName(@RequestParam(value = "name", required = false) String name) throws BusinessException {
        List<WordDo> wordDoList = wordService.selectByUserName(name);
        if (wordDoList.isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        return (CommonReturnType) CommonReturnType.create(wordDoList);
    }

    /**
     * 通过问题ID来查询
     *
     * @param id 问题ID
     * @return wordDoList
     * @throws BusinessException 异常
     */
    @RequestMapping(value = "/selectByQuestionID", method = RequestMethod.POST)
    @ResponseBody
    public Object selectByQuestionID(@RequestParam(value = "questionID", required = false) Integer id) throws BusinessException {
        if (String.valueOf(id).trim().isEmpty()) {
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        List<WordDo> wordDoList = wordService.selectByQuestionID(id);
        return CommonReturnType.create(wordDoList);
    }
}
