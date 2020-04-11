package com.imooc.li.car.error;

/**
 * @author LI
 */

public enum EmBusinessError implements Commonerror {
    /**
     * 定义各种可能出现的情况
     */
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    LOGIN_FAIL(10002, "用户名或密码错误"),
    NO_DATA(1003, "无数据"),
    UPDATE(1004, "更新失败"),
    INSERT(1005, "插入失败"),
    HAVING(1006, "用户名已存在！");
    private int code;
    private String error;

    EmBusinessError(int code, String error) {
        this.code = code;
        this.error = error;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String gerError() {
        return this.error;
    }

    @Override
    public Commonerror setError(String error) {
        this.error = error;
        return this;
    }
}
