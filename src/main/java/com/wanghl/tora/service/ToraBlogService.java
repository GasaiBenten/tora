package com.wanghl.tora.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.tora.entity.ToraBlog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanghl.tora.entity.vo.BlogInfo;
import com.wanghl.tora.entity.vo.QueryBlogVo;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanghl
 * @since 2021-03-26
 */
public interface ToraBlogService extends IService<ToraBlog> {

    List<ToraBlog> queryBlogPage(Page<ToraBlog> page, QueryBlogVo blogVo);

    void addBlog(ToraBlog toraBlog, HttpSession session);

    List<ToraBlog> queryHistoryPage(Page<ToraBlog> page, QueryBlogVo blogVo);

    void resumeById(String id);

    List<ToraBlog> getHistoryPage(Page<ToraBlog> page);

    ToraBlog getByBlogId(String id);

    void updateBlogById(ToraBlog toraBlog);

    long selectDeletedCount();

    long selectCount();

    void resumeBatchByIds(List idList);
}
