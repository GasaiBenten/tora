package com.wanghl.tora.entity.vo;

import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * @author wanghl
 * @date 2021/3/26 - 13:46
 */
@Data
public class RegisterVo {
    private String username;
    private String password;
    private String email;
    private String code;
}
