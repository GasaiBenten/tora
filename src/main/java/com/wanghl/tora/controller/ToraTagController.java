package com.wanghl.tora.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.tora.entity.ToraTag;
import com.wanghl.tora.entity.ToraUser;
import com.wanghl.tora.entity.vo.*;
import com.wanghl.tora.result.Result;
import com.wanghl.tora.service.ToraTagService;
import javafx.scene.control.TableView;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanghl
 * @since 2021-03-26
 */
@RestController
@RequestMapping("/tora/tag")
@CrossOrigin
public class ToraTagController {

    @Autowired
    private ToraTagService tagService;

    //查询所有标签
    @GetMapping("getAllTag")
    public Result getAllTag(){
        List<ToraTag> tagList = tagService.list(null);
        return Result.ok().data("tagList",tagList);
    }

    //查询所有标签带分页
    @GetMapping("tagPage/{current}/{limit}")
    public Result tagPage(@PathVariable long current,
                           @PathVariable long limit){
        Page<ToraTag> page = new Page<>(current,limit);
        tagService.page(page,null);
        List<ToraTag> tagList = page.getRecords();
        return Result.ok().data("tagList",tagList);
    }

    //查询所有标签带分页带条件
    @PostMapping("queryTagPage/{current}/{limit}")
    public Result queryTagPage(@PathVariable long current,
                                @PathVariable long limit,
                                @RequestBody(required = false) TagQueryVo tagQueryVo){
        Page<ToraTag> page = new Page<>(current,limit);
        List<ToraTag> records = tagService.queryTagPage(tagQueryVo,page);
        return Result.ok().data("records",records);
    }

    //添加标签
    @PostMapping("addTag")
    public Result addTag(@RequestBody TagVo tagVo){
        tagService.addTag(tagVo);
        return Result.ok();
    }

    //根据id删除标签
    @DeleteMapping("deleteByTagId/{id}")
    public Result deleteByTagId(@PathVariable String id){
        boolean flag = tagService.removeById(Integer.parseInt(id));
        return flag ? Result.ok() : Result.error().message("删除失败");
    }

    //根据id恢复标签
    @GetMapping("resumeById/{id}")
    public Result resumeById(@PathVariable String id){
        List idList = new ArrayList();
        idList.add(id);
        tagService.resumeById(idList);
        return Result.ok();
    }

    //根据id查询标签
    @GetMapping("getTagById/{id}")
    public Result getTagById(@PathVariable String id){
        ToraTag tag = tagService.getById(id);
        return Result.ok().data("tag",tag);
    }

    //修改标签
    @PostMapping("updateTag")
    public Result updateTag(@RequestBody ToraTag tag){
        boolean flag = tagService.updateById(tag);
        return flag ? Result.ok() : Result.error().message("更新失败");
    }

    //批量查询
    @GetMapping("getBatchTag")
    public Result getBatchTag(String ids){
        List<ToraTag> tagList = tagService.getBatchTag(ids);
        return Result.ok().data("tagList",tagList);
    }

    //批量删除
    @PostMapping("removeBatch")
    public Result removeBatch(@RequestBody List<ToraTag> tagList){
        List ids = new ArrayList();
        for (int i = 0; i < tagList.size(); i++) {
            ids.add(tagList.get(i).getId());
        }
        tagService.removeByIds(ids);
        return Result.ok();
    }


    //批量恢复
    @PostMapping("resumeBatch")
    public Result resumeBatch(@RequestBody List<ToraTag> tagList){
        List idList = new ArrayList();
        for (int i = 0; i < tagList.size(); i++) {
            idList.add(tagList.get(i).getId());
        }
        tagService.resumeById(idList);
        return Result.ok();
    }

    @GetMapping("getAllDeleted")
    public Result getAllDeleted(){
        List<ToraTag> records = tagService.getAllDeleted();
        return Result.ok().data("records",records);
    }
}

