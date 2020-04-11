package com.imooc.li.car.error;

/**
 * @author LI
 */
public interface Commonerror {
    /**
     * 得到错误信息的code
     *
     * @return code
     */
    int getCode();

    /**
     * 得到错误信息
     *
     * @return 错误信息
     */
    String gerError();

    /**
     * 设置错误信息
     *
     * @param error 错误信息
     * @return 错误信息
     */
    Commonerror setError(String error);
}
