package com.wanghl.tora.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.tora.entity.ToraClassify;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanghl.tora.entity.vo.ClassifyQueryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanghl
 * @since 2021-03-26
 */
public interface ToraClassifyService extends IService<ToraClassify> {

    ToraClassify getClassifyByBlogId(String id);

    List<ToraClassify> queryClassifyPage(Page<ToraClassify> page, ClassifyQueryVo classifyQueryVo);

    long selectCount();

    void resumeById(List ids);

    long selectDeletedCount();

    List<ToraClassify> getAllDeleted();

    List<ToraClassify> getAllDeletedPage(Page<ToraClassify> page);
}
