package com.test.redisDemo.util;

public class JsonData {

    /**
     * 0 表示成功
     */
    private Integer code;

    /**
     * 数据
     */
    private Object data;

    /**
     * 描述
     */
    private String msg;



    public JsonData(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;

    }


    public static JsonData buildSuccess(){return new JsonData(0, null, null); }

    /**
     * 成功则不传数据
     * @return
     */
    public static JsonData buildSuccess(Object data) {
        return new JsonData(0, data, null);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 失败则传入失败信息
     * @param msg
     * @return
     */
    public static JsonData buildError(String msg){
        return new JsonData(-1, null, msg);
    }

    @Override
    public String toString() {
        return "JsonData{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
