package com.wanghl.tora.controller;


import com.wanghl.tora.entity.ToraTag;
import com.wanghl.tora.result.Result;
import com.wanghl.tora.service.ToraBlogService;
import com.wanghl.tora.service.ToraBlogTagService;
import com.wanghl.tora.service.ToraTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/tora/blogtag")
@CrossOrigin
public class ToraBlogTagController {

    @Autowired
    private ToraBlogTagService blogTagService;

    //根据博客id查询所有tag
    @GetMapping("getAllTagByBlogId/{id}")
    public Result getAllTagByBlogId(@PathVariable String id){
        List<ToraTag> tagList = blogTagService.getAllTagByBlogId(id);
        return Result.ok().data("tagList",tagList);
    }

}

