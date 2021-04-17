package com.wanghl.tora.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.beans.Transient;
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
@ApiModel(value="ToraBlog对象", description="")
public class ToraBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "是否发布")
    private boolean tPublish;

    @ApiModelProperty(value = "是否开启评论")
    private boolean tComment;

    @ApiModelProperty(value = "是否推荐")
    private boolean tRecommend;

    private String classify;

    private String username;

    private String title;

    private String cover;

    private String content;

    private String type;

    @TableField(exist = false)
    private List<ToraTag> tagList;

    @TableField(exist = false)
    private Integer[] tagIds;

    @TableLogic
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


    public void initTagIds(){
        this.tagIds = tagsToIds(this.getTagList());
    }

    private Integer[] tagsToIds(List<ToraTag> tags){
        if (tags!=null){
            Integer[] ids = new Integer[tags.size()];
            for (int i = 0; i < tags.size(); i++) {
                ids[i] = tags.get(i).getId();
            }
            return ids;
        }else {
            return tagIds;
        }
    }


}
