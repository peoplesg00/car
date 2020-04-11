package com.imooc.li.car.response;

public class CommonReturnType {
    /**
     * 辨明返回的结果为true或是fail
     */
    private String status;
    /**
     * 若status=success，则data内返回前端需要的json数据
     * 若status=fail，则data内使用通用的错误码格式
     */
    private Object data;

    public static Object create(Object result) {
        return CommonReturnType.create(result, "success");
    }

    private static Object create(Object result, String success) {
        CommonReturnType type = new CommonReturnType();
        type.setData(result);
        type.setStatus(success);
        return type;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
