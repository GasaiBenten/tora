package com.wanghl.tora.mapper;

import com.wanghl.tora.entity.ToraComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wanghl
 * @since 2021-03-26
 */
@Mapper
public interface ToraCommentMapper extends BaseMapper<ToraComment> {

    List<ToraComment> queryCommentPage(@Param("current") long current,
                                       @Param("size") long size,
                                       @Param("title") String title,
                                       @Param("keyWord") String keyWord,
                                       @Param("username") String username,
                                       @Param("status") Integer status,
                                       @Param("begin") String begin);

    @Update("update tora_comment set t_publish=-1 where id= #{id} and is_delete=0")
    Integer reject(String id);

    @Update("update tora_comment set t_publish=1 where id = #{id} and is_delete=0")
    void adoptBatch(String id);

    @Update("update tora_comment set is_delete=0 where id=#{id} and is_delete=1")
    void resume(Integer id);

    @Select("select * from tora_comment where is_delete=1")
    List<ToraComment> getAllDeleted();

    @Select("select count(id) from tora_comment where is_delete=1")
    Integer getDeletedCount();

    List<ToraComment> queryDeletedPage(@Param("current") long current,
                                        @Param("size") long size,
                                        @Param("title") String title,
                                        @Param("keyWord") String keyWord,
                                        @Param("username") String username,
                                        @Param("status") Integer status,
                                        @Param("begin") String begin);

    List<ToraComment> selectComment(@Param("size") long size, @Param("current") long current,@Param("blogId") String id);

    List<ToraComment> selectReply(@Param("blogId") String id);

    @Select("select count(id) from tora_comment where blog_id = #{id} and parent_id=0 and t_publish=1 and is_delete=0")
    Integer selectBlogCommentCount(String id);

    long selectCommentCount(@Param("status") Integer status);

}
