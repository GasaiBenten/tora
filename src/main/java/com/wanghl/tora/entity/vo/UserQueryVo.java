package com.wanghl.tora.entity.vo;

import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.Date;

/**
 * @author wanghl
 * @date 2021/3/26 - 14:49
 */
@Data
public class UserQueryVo {

    private String username;
    private String email;
    private String begin;
    private String end;
}
