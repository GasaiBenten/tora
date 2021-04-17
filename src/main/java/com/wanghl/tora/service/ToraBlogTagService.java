package com.wanghl.tora.service;

import com.wanghl.tora.entity.ToraBlogTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanghl.tora.entity.ToraTag;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanghl
 * @since 2021-03-26
 */
public interface ToraBlogTagService extends IService<ToraBlogTag> {

    List<ToraTag> getAllTagByBlogId(String id);

}
