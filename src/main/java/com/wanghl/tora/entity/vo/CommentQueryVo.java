package com.wanghl.tora.entity.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wanghl
 * @date 2021/3/27 - 15:23
 */
@Data
public class CommentQueryVo {

    private String title;

    private String username;

    private String keyWord;

    private Integer isDelete;

    private Integer status; //1审核通过  0未审核  -1审核未通过

    private String begin;

    private String end;

}
