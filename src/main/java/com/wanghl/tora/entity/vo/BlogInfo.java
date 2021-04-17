package com.wanghl.tora.entity.vo;

import com.wanghl.tora.entity.ToraTag;
import lombok.Data;
import org.springframework.web.util.pattern.PathPattern;

import java.util.Date;
import java.util.List;

/**
 * @author wanghl
 * @date 2021/3/27 - 13:48
 */
@Data
public class BlogInfo {
    private Integer id;
    private String title;
    private String content;
    private String username;
    private String cover;
    private String type;
    private String classify;
    private String tagIds;
    private List<ToraTag> tagList;
    private boolean tPublish;
    private boolean tComment;
    private boolean tRecommend;

    public void init(){
        this.tagIds = tagsToIds(this.getTagList());
    }


    private String tagsToIds(List<ToraTag> tags){
        if (!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (ToraTag tag : tags){
                if (flag){
                    ids.append(",");
                }else{
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else {
            return tagIds;
        }
    }
}
