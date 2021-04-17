package com.wanghl.tora.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.tora.entity.ToraTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanghl.tora.entity.vo.TagInfoVo;
import com.wanghl.tora.entity.vo.TagQueryVo;
import com.wanghl.tora.entity.vo.TagVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanghl
 * @since 2021-03-26
 */
public interface ToraTagService extends IService<ToraTag> {

    List<ToraTag> queryTagPage(TagQueryVo tagQueryVo, Page<ToraTag> page);

    void addTag(TagVo tagVo);

    boolean updateTag(TagInfoVo tagInfo);

    List<ToraTag> getBatchTag(String ids);

    void resumeById(List idList);

    List<ToraTag> getAllDeleted();
}
