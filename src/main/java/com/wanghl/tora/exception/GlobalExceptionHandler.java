package com.wanghl.tora.exception;

import com.wanghl.tora.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wanghl
 * @date 2021/3/26 - 13:54
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    //全局异常
    @ExceptionHandler(Exception.class) //指定出现什么异常执行这个方法
    @ResponseBody //返回数据
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("执行了全局异常处理");
    }

    //自定义异常
    @ExceptionHandler(MyException.class) //指定出现什么异常执行这个方法
    @ResponseBody //返回数据
    public Result error(MyException e){
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }

    //空指针
    @ExceptionHandler(NullPointerException.class) //指定出现什么异常执行这个方法
    @ResponseBody //返回数据
    public Result error(NullPointerException e){
        e.printStackTrace();
        return Result.error().message("发生空指针异常");
    }
}
