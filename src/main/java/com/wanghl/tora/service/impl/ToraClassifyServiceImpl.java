package com.wanghl.tora.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.tora.entity.ToraBlog;
import com.wanghl.tora.entity.ToraClassify;
import com.wanghl.tora.entity.vo.ClassifyQueryVo;
import com.wanghl.tora.mapper.ToraClassifyMapper;
import com.wanghl.tora.service.ToraBlogService;
import com.wanghl.tora.service.ToraClassifyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ToraClassifyServiceImpl extends ServiceImpl<ToraClassifyMapper, ToraClassify> implements ToraClassifyService {

    @Autowired
    private ToraBlogService blogService;

    @Override
    public ToraClassify getClassifyByBlogId(String id) {
        ToraBlog toraBlog = blogService.getById(id);
        String classify = toraBlog.getClassify();
        QueryWrapper<ToraClassify> wrapper = new QueryWrapper<>();
        wrapper.eq("name",classify);
        ToraClassify toraClassify = baseMapper.selectOne(wrapper);
        return toraClassify;
    }

    @Override
    public List<ToraClassify> queryClassifyPage(Page<ToraClassify> page, ClassifyQueryVo classifyQueryVo) {
        String name = classifyQueryVo.getName();
        String begin = classifyQueryVo.getBegin();
        String end = classifyQueryVo.getEnd();
        QueryWrapper<ToraClassify> wrapper = new QueryWrapper<>();
        if (!name.equals("")){
            wrapper.like("name",name);
        }
        if (!begin.equals("")){
            wrapper.gt("gmt_create",begin);
        }
        if (!end.equals("")){
            wrapper.lt("gmt_create",end);
        }
        List<ToraClassify> records = baseMapper.selectPage(page, wrapper).getRecords();
        return records;
    }

    @Override
    public long selectCount() {
        Integer count = baseMapper.selectCount(null);
        return count;
    }

    @Override
    public void resumeById(List ids) {
        for (int i = 0; i < ids.size(); i++) {
            baseMapper.resumeById(ids.get(i).toString());
        }
    }

    @Override
    public long selectDeletedCount() {
        long count = baseMapper.selectDeletedCount();
        return count;
    }

    @Override
    public List<ToraClassify> getAllDeleted() {
        List<ToraClassify> records = baseMapper.selectDeleted();
        return records;
    }

    @Override
    public List<ToraClassify> getAllDeletedPage(Page<ToraClassify> page) {
        long size = page.getSize();
        long current = (page.getCurrent()-1)*size;
        List<ToraClassify> classifyList = baseMapper.getAllDeletedPage(size,current);
        return classifyList;
    }
}
