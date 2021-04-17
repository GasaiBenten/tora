package com.wanghl.tora.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.tora.entity.ToraComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanghl.tora.entity.vo.CommentQueryVo;
import com.wanghl.tora.entity.vo.CommentVo;

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
public interface ToraCommentService extends IService<ToraComment> {

    List<ToraComment> queryCommentPage(Page<ToraComment> page, CommentQueryVo commentQueryVo);

    List<ToraComment> getCommentByBlogId(Page<ToraComment> page, String id);

    void addComment(CommentVo comment, HttpSession session);

    void adopt(String id);

    boolean reject(String id);

    void adoptBatch(List ids);

    void resume(Integer id);

    List<ToraComment> queryDeletedPage(Page<ToraComment> page, CommentQueryVo commentQueryVo);

    long selectDeletedCount();

    long selectBlogCommentCount(String id);

    long selectCommentCount(Integer status);

}
