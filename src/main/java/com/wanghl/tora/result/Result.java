package com.wanghl.tora.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanghl
 * @date 2021/3/26 - 13:14
 */
@Data
public class Result {
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String,Object> data = new HashMap<>();

    //构造方法私有化
    private Result(){}

    //静态方法
    public static Result ok(){
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("成功");
        return result;
    }
    public static Result error(){
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR);
        result.setMessage("失败");
        return result;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result data(String key, Object val){
        this.data.put(key, val);
        return this;
    }

    public Result data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
