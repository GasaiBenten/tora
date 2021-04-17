package com.wanghl.tora.entity.vo;

import lombok.Data;

/**
 * @author wanghl
 * @date 2021/3/26 - 16:52
 */
@Data
public class QueryBlogVo {
    private String title;
    private String classify;
    private String tagId;
    private Integer status;
    private String username;
    private String type;
    private String begin;
    private String end;
}
