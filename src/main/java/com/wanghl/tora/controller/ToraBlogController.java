package com.wanghl.tora.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.tora.entity.ToraBlog;
import com.wanghl.tora.entity.vo.BlogInfo;
import com.wanghl.tora.entity.vo.QueryBlogVo;
import com.wanghl.tora.result.Result;
import com.wanghl.tora.service.ToraBlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanghl
 * @since 2021-03-26
 */
@RestController
@RequestMapping("/tora/blog")
@CrossOrigin
@Api(value = "博客接口")
public class ToraBlogController {

    @Autowired
    private ToraBlogService blogService;

    //查询所有博客
    @GetMapping("getAllBlog")
    public Result getAllBlog(){
        blogService.list(null);
        return Result.ok();
    }

    //查询所有博客带分页
    @GetMapping("blogPage/{current}/{limit}")
    public Result blogPage(@PathVariable long current,
                           @PathVariable long limit){
        Page<ToraBlog> page = new Page<>(current,limit);
        List<ToraBlog> records = blogService.page(page, null).getRecords();
        return Result.ok().data("records",records);
    }

    //查询所有博客带分页带条件
    @PostMapping("queryBlogPage/{current}/{limit}")
    public Result queryBlogPage(@PathVariable long current,
                                @PathVariable long limit,
                                @RequestBody(required = false) QueryBlogVo blogVo){
        Page<ToraBlog> page = new Page<>(current,limit);
        List<ToraBlog> records = blogService.queryBlogPage(page,blogVo);
        if (records.size()==0){
            return Result.error().message("无查询结果");
        }
        long total = page.getTotal();
        return Result.ok().data("records",records).data("total",total);
    }

    //根据博客id查询博客
    @GetMapping("getByBlogId/{id}")
    public Result getByBlogId(@PathVariable String id){
        ToraBlog toraBlog = blogService.getByBlogId(id);
        return Result.ok().data("blog",toraBlog);
    }

    //添加博客
    @PostMapping("addBlog")
    public Result addBlog(@RequestBody ToraBlog toraBlog,
                          HttpSession session){
        blogService.addBlog(toraBlog,session);
        return Result.ok();
    }

    //根据博客id删除博客
    @DeleteMapping("deleteBlogById/{id}")
    public Result deleteBlogById(@PathVariable String id){
        boolean flag = blogService.removeById(Integer.parseInt(id));
        long total = blogService.selectCount();
        return flag ? Result.ok().data("total",total) : Result.error().message("删除失败");
    }

    //根据博客id修改博客
    @PostMapping("updateBlogById")
    public Result updateBlogById(@RequestBody ToraBlog toraBlog){
        blogService.updateBlogById(toraBlog);
        return Result.ok();
    }

    //查询历史记录带分页
    @GetMapping("getHistoryPage/{current}/{limit}")
    public Result getHistoryPage(@PathVariable long current,
                                 @PathVariable long limit){
        Page<ToraBlog> page = new Page<>(current,limit);
        List<ToraBlog> records = blogService.getHistoryPage(page);
        return Result.ok().data("records",records);
    }


    //查询历史删除带分页带条件
    @ApiOperation(value = "查询历史删除带分页带条件")
    @PostMapping("queryHistoryPage/{current}/{limit}")
    public Result queryHistoryPage(@PathVariable long current,
                                   @PathVariable long limit,
                                   @RequestBody(required = false) QueryBlogVo blogVo){
        Page<ToraBlog> page = new Page<>(current,limit);
        List<ToraBlog> records = blogService.queryHistoryPage(page,blogVo);
        if (records.size()==0){
            return Result.error().message("无查询结果");
        }
        long total = blogService.selectDeletedCount();
        return Result.ok().data("records",records).data("total",total);
    }

    //恢复记录
    @ApiOperation(value = "恢复记录")
    @GetMapping("resumeById/{id}")
    public Result resumeById(@PathVariable String id){
        blogService.resumeById(id);
        long total = blogService.selectDeletedCount();
        return Result.ok().data("total",total);
    }

    //批量删除
    @ApiOperation(value = "批量删除")
    @PostMapping("deleteBatch")
    public Result deleteBatch(@RequestBody List<ToraBlog> selections){
        List idList = new ArrayList();
        for (int i = 0; i < selections.size(); i++) {
            idList.add(selections.get(i).getId());
        }
        blogService.removeByIds(idList);
        long total = blogService.selectCount();
        return Result.ok().data("total",total);
    }

    //批量恢复
    @ApiOperation(value = "批量恢复")
    @PostMapping("resumeBatch")
    public Result resumeBatch(@RequestBody List<ToraBlog> selections){
        List idList = new ArrayList();
        for (int i = 0; i < selections.size(); i++) {
            idList.add(selections.get(i).getId());
        }
        blogService.resumeBatchByIds(idList);
        long total = blogService.selectDeletedCount();
        return Result.ok().data("total",total);
    }
}

