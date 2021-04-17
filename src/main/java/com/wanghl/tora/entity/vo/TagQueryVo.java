package com.wanghl.tora.entity.vo;

import lombok.Data;
import org.springframework.web.util.pattern.PathPattern;

/**
 * @author wanghl
 * @date 2021/3/26 - 15:19
 */
@Data
public class TagQueryVo {

    private String name;
    private String begin;
    private String end;
}
