package com.yao.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @className: Result
 * @Description: 统一返回结果
 * @author: long
 * @date: 2023/3/6 23:35
 */
@Data
public class Result implements Serializable {

    private String code;

    private String msg;

    private Object data;


    public static Result succ(Object data) {
        Result m = new Result();
        m.setCode("0");
        m.setData(data);
        m.setMsg("操作成功");
        return m;
    }

    public static Result succ(String mess) {
        Result m = new Result();
        m.setCode("0");
        m.setData(null);
        m.setMsg(mess);
        return m;
    }


    public static Result succ(String mess, Object data) {
        Result m = new Result();
        m.setCode("0");
        m.setData(data);
        m.setMsg(mess);
        return m;
    }


    public static Result fail(String mess) {
        Result m = new Result();
        m.setCode("-1");
        m.setData(null);
        m.setMsg(mess);
        return m;
    }


    public static Result fail(String mess, Object data) {
        Result m = new Result();
        m.setCode("-1");
        m.setData(data);
        m.setMsg(mess);
        return m;
    }


    public static Result fail(String i, String mess, Object data) {
        Result m = new Result();
        m.setCode(i);
        m.setMsg(mess);
        m.setData(data);
        return m;

    }


}
