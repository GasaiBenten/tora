package com.wanghl.tora.entity.vo;

import lombok.Data;
import org.springframework.web.util.pattern.PathPattern;

/**
 * @author wanghl
 * @date 2021/3/27 - 22:32
 */
@Data
public class CommentVo {
    private Integer id;
    private String content;
    private Integer blogId;
    private Integer parentId;
    private Integer userId;

}
