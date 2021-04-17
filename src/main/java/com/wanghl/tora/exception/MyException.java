package com.wanghl.tora.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanghl
 * @date 2021/3/26 - 13:52
 */
@Data
@AllArgsConstructor  //生成有参构造方法
@NoArgsConstructor   //生成无参构造方法
public class MyException extends RuntimeException{
    @ApiModelProperty(value = "自定义异常状态码")
    private Integer code;

    private String msg;
}
