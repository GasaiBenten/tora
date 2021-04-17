package com.wanghl.tora.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wanghl
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ToraComment对象", description="")
public class ToraComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer blogId;

    private Integer parentId;

    private Integer userId;

    private String content;

    @TableLogic
    private Integer isDelete;

    private Integer tPublish;

    @TableField(exist = false)
    private String title;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String avatar;

    @TableField(exist = false)
    private List<ToraComment> children;

    @TableField(exist = false)
    private Integer level;

    @TableField(exist = false)
    private String to;

    @TableField(exist = false)
    private String from;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
