package com.wanghl.tora.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.tora.entity.ToraBlogTag;
import com.wanghl.tora.entity.ToraTag;
import com.wanghl.tora.entity.ToraUser;
import com.wanghl.tora.mapper.ToraBlogTagMapper;
import com.wanghl.tora.service.ToraBlogTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanghl.tora.service.ToraTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanghl
 * @since 2021-03-26
 */
@Service
public class ToraBlogTagServiceImpl extends ServiceImpl<ToraBlogTagMapper, ToraBlogTag> implements ToraBlogTagService {

    @Autowired
    private ToraTagService tagService;

    @Override
    public List<ToraTag> getAllTagByBlogId(String id) {
        QueryWrapper<ToraBlogTag> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id",id);
        List<ToraBlogTag> toraBlogTags = baseMapper.selectList(wrapper);
        if (toraBlogTags.size()>0){
            Integer[] tagIds = new Integer[toraBlogTags.size()];
            for (int i = 0; i < toraBlogTags.size(); i++) {
                tagIds[i] = toraBlogTags.get(i).getTagId();
            }
            QueryWrapper<ToraTag> tagQueryWrapper = new QueryWrapper<>();
            tagQueryWrapper.in("id",tagIds);
            List<ToraTag> tagList = tagService.list(tagQueryWrapper);
            return tagList;
        }
        return null;
    }

}
