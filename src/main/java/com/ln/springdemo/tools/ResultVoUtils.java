package com.ln.springdemo.tools;

public class ResultVoUtils {
    public static ResultVo success(String status){
        return new ResultVo(ResultVo.SUSSESSCODE,"执行成功",status);
    }

    public static ResultVo success(String message,String status){
        return new ResultVo(ResultVo.SUSSESSCODE,message,status);
    }

    public static ResultVo success(String message,Object data){
        return new ResultVo(ResultVo.SUSSESSCODE,message,data);
    }

    public static ResultVo success(String message, String status,Object data){
        return new ResultVo(ResultVo.SUSSESSCODE,message,data,status);
    }

    public static ResultVo success(String message,Object data, String status,Long count){
        return new ResultVo(ResultVo.SUSSESSCODE,message,data,status,count);
    }

    public static ResultVo error(String message,String status){
        return new ResultVo(ResultVo.ERRORCODE,message,status);
    }



}
