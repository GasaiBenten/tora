package com.wanghl.tora.mapper;

import com.wanghl.tora.entity.ToraClassify;
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
public interface ToraClassifyMapper extends BaseMapper<ToraClassify> {

    @Update("update tora_classify set is_deleted=0 where id=#{id} and is_deleted=1")
    void resumeById(String id);

    @Select("select count(id) from tora_classify where is_deleted=1")
    long selectDeletedCount();

    @Select("select * from tora_classify where is_deleted=1")
    List<ToraClassify> selectDeleted();

    @Select("select * from tora_classify where is_deleted=1 limit #{current},#{size}")
    List<ToraClassify> getAllDeletedPage(@Param("size") long size, @Param("current") long current);
}
