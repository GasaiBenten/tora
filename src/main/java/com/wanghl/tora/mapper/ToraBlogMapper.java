package com.wanghl.tora.mapper;

import com.wanghl.tora.entity.ToraBlog;
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
public interface ToraBlogMapper extends BaseMapper<ToraBlog> {

    @Select("select * from tora_blog where is_deleted=1 limit #{current},#{size}")
    List<ToraBlog> selectDeleted(@Param("current") long current, @Param("size") long size);

    List<ToraBlog> selectDeletedByQuery(@Param("current") long current, @Param("size") long size, @Param("begin") String begin, @Param("title") String title, @Param("username") String username);

    @Update("update tora_blog set is_deleted=0 where id=#{id} and is_deleted=1")
    void resumeById(String id);

    @Select("select count(1) from tora_blog where is_deleted =1")
    long selectDeletedCount();
}
