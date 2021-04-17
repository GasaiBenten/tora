package com.wanghl.tora.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.tora.entity.ToraTag;
import com.wanghl.tora.entity.ToraUser;
import com.wanghl.tora.entity.vo.TagInfoVo;
import com.wanghl.tora.entity.vo.TagQueryVo;
import com.wanghl.tora.entity.vo.TagVo;
import com.wanghl.tora.exception.MyException;
import com.wanghl.tora.mapper.ToraTagMapper;
import com.wanghl.tora.result.ResultCode;
import com.wanghl.tora.service.ToraTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
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
public class ToraTagServiceImpl extends ServiceImpl<ToraTagMapper, ToraTag> implements ToraTagService {

    @Override
    public List<ToraTag> queryTagPage(TagQueryVo tagQueryVo, Page<ToraTag> page) {
        String name = tagQueryVo.getName().trim();
        String begin = tagQueryVo.getBegin().trim();
        String end = tagQueryVo.getEnd().trim();
        QueryWrapper<ToraTag> wrapper = new QueryWrapper<>();
        if (!name.equals("")){
            wrapper.like("name",name);
        }
        if (!begin.equals("")){
            wrapper.gt("gmt_create",begin);
        }
        if (!end.equals("")){
            wrapper.lt("gmt_create",end);
        }
        baseMapper.selectPage(page,wrapper);
        List<ToraTag> records = page.getRecords();
        return records;
    }

    @Override
    public void addTag(TagVo tagVo) {
        QueryWrapper<ToraTag> wrapper = new QueryWrapper<>();
        wrapper.eq("name",tagVo.getName().trim());
        ToraTag one = baseMapper.selectOne(wrapper);
        if (one != null){
            throw new MyException(ResultCode.ERROR,"标签重复");
        }
        ToraTag toraTag = new ToraTag();
        BeanUtils.copyProperties(tagVo,toraTag);
        baseMapper.insert(toraTag);
    }

    @Override
    public boolean updateTag(TagInfoVo tagInfo) {
        if (tagInfo.getName().trim().equals("")){
            throw new MyException(ResultCode.ERROR,"输入信息不能为空");
        }
        ToraTag toraTag = baseMapper.selectById(tagInfo.getId());
        toraTag.setName(tagInfo.getName().trim());
        int i = baseMapper.updateById(toraTag);
        return i > 0 ? true : false;
    }

    @Override
    public List<ToraTag> getBatchTag(String ids) {
        List<ToraTag> tagList = baseMapper.selectBatchIds(convertToList(ids));
        return tagList;
    }

    @Override
    public void resumeById(List idList) {
        for (int i = 0; i < idList.size(); i++) {
            baseMapper.resumeById(idList.get(i).toString());
        }
    }

    @Override
    public List<ToraTag> getAllDeleted() {
        return baseMapper.selectDeleted();
    }

    private List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids!=null){
            String[] idarry = ids.split(",");
            for (int i=0;i< idarry.length;i++){
                list.add(new Long(idarry[i]));
            }
        }
        return list;
    }


}
