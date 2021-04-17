package com.wanghl.tora.mapper;

import com.wanghl.tora.entity.ToraTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
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
public interface ToraTagMapper extends BaseMapper<ToraTag> {

    @Update("update tora_tag set is_deleted=0 where id=#{id} and is_deleted=1")
    void resumeById(String id);

    @Select("select count(id) from tora_tag where is_deleted=1")
    long selectDeleteCount();

    @Select("select * from tora_tag where is_deleted=1")
    List<ToraTag> selectDeleted();
}
