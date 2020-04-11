package com.imooc.li.car.error;

/**
 * @author LI
 */
public class BusinessException extends Exception implements Commonerror {

    private Commonerror commonerror;

    public BusinessException(Commonerror commonerror) {
        super();
        this.commonerror = commonerror;
    }

    public BusinessException(Commonerror commonerror, String error) {
        super();
        this.commonerror = commonerror;
        commonerror.setError(error);
    }

    @Override
    public int getCode() {
        return this.commonerror.getCode();
    }

    @Override
    public String gerError() {
        return this.commonerror.gerError();
    }

    @Override
    public Commonerror setError(String error) {
        this.commonerror.setError(error);
        return this;
    }
}
