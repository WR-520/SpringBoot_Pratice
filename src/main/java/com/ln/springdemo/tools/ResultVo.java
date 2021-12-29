package com.ln.springdemo.tools;

import lombok.Data;

/**
 * 封装结果集工具类
 */
//@Data 注解的主要作用是提高代码的简洁，
// 使用这个注解可以省去代码中大量的get()、 set()、 toString()等方法；
@Data
public class ResultVo<T> {
    public static final Integer SUSSESSCODE = 0;
    public static final Integer ERRORCODE = 1;
    private Integer code;
    private String message;
    private T Data;
    private String status;
    private Long count;

    public ResultVo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultVo(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        Data = data;
    }

    public ResultVo(Integer code, String message, T data, String status) {
        this.code = code;
        this.message = message;
        Data = data;
        this.status = status;
    }

    public ResultVo(Integer code, String message, T data, String status, Long count) {
        this.code = code;
        this.message = message;
        Data = data;
        this.status = status;
        this.count = count;
    }

}
