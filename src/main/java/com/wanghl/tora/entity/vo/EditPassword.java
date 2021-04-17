package com.wanghl.tora.entity.vo;

import lombok.Data;

/**
 * @author wanghl
 * @date 2021/4/6 - 16:54
 */
@Data
public class EditPassword {
    private Integer id;
    private String pass;
    private String checkPass;
    private String oldPass;
}
