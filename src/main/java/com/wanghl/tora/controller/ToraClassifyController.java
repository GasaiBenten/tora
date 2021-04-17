package com.wanghl.tora.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.tora.entity.ToraClassify;
import com.wanghl.tora.entity.vo.ClassifyQueryVo;
import com.wanghl.tora.result.Result;
import com.wanghl.tora.service.ToraClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/tora/classify")
@CrossOrigin
public class ToraClassifyController {

    @Autowired
    private ToraClassifyService classifyService;

    //查询所有分类
    @GetMapping("getAllClassify")
    public Result getAllClassify(){
        List<ToraClassify> classifyList = classifyService.list(null);
        return Result.ok().data("classifyList",classifyList);
    }

    //根据博客id查询分类
    @GetMapping("getClassifyByBlogId/{id}")
    public Result getClassifyByBlogId(@PathVariable String id){
        ToraClassify classify = classifyService.getClassifyByBlogId(id);
        return Result.ok().data("classify",classify);
    }

    //添加分类
    @PostMapping("addClassify")
    public Result addClassify(@RequestBody ToraClassify classify){
        boolean flag = classifyService.save(classify);
        return flag ? Result.ok() : Result.error().message("添加失败");
    }

    //编辑分类
    @PostMapping("updateClassify")
    public Result updateClassify(@RequestBody ToraClassify classify){
        classifyService.updateById(classify);
        return Result.ok();
    }

    //根据分类id删除分类
    @DeleteMapping("deleteClassifyById/{id}")
    public Result deleteClassifyById(@PathVariable String id){
        boolean flag = classifyService.removeById(Integer.parseInt(id));
        return flag ? Result.ok() : Result.error().message("删除失败");
    }

    //查询所有分类带分页
    @GetMapping("classifyPage/{current}/{limit}")
    public Result classifyPage(@PathVariable long current,
                               @PathVariable long limit){
        Page<ToraClassify> page = new Page<>(current,limit);
        List<ToraClassify> records = classifyService.page(page, null).getRecords();
        long total = page.getTotal();
        return Result.ok().data("records",records).data("total",total);
    }


    //查询所有分类带分页带条件
    @GetMapping("queryClassifyPage/{current}/{limit}")
    public Result queryClassifyPage(@PathVariable long current,
                                    @PathVariable long limit,
                                    @RequestBody(required = false) ClassifyQueryVo classifyQueryVo){
        Page<ToraClassify> page = new Page<>(current,limit);
        List<ToraClassify> records = classifyService.queryClassifyPage(page,classifyQueryVo);
        if (records.size() == 0){
            return Result.error().message("无查询结果");
        }
        return Result.ok().data("records",records);
    }

    @PostMapping("removeBatch")
    public Result removeBatch(@RequestBody List<ToraClassify> selections){
        List ids = new ArrayList(selections.size());
        for (int i = 0; i < selections.size(); i++) {
            ids.add(selections.get(i).getId());
        }
        classifyService.removeByIds(ids);
        long total = classifyService.selectCount();
        return Result.ok().data("total",total);
    }

    @GetMapping("resumeById/{id}")
    public Result resumeById(@PathVariable String id){
        List idList = new ArrayList();
        idList.add(id);
        classifyService.resumeById(idList);
        long total = classifyService.selectDeletedCount();
        return Result.ok().data("total",total);
    }

    @PostMapping("resumeBatchByIds")
    public Result resumeBatchByIds(@RequestBody List<ToraClassify> selections){
        List ids = new ArrayList(selections.size());
        for (int i = 0; i < selections.size(); i++) {
            ids.add(selections.get(i).getId());
        }
        classifyService.resumeById(ids);
        long total = classifyService.selectDeletedCount();
        return Result.ok().data("total",total);
    }

    @GetMapping("getAllDeleted")
    public Result getAllDeleted(){
        List<ToraClassify> records = classifyService.getAllDeleted();
        return Result.ok().data("records",records);
    }

    @GetMapping("getAllDeletedPage/{current}/{limit}")
    public Result getAllDeletedPage(@PathVariable long current,
                                    @PathVariable long limit){
        Page<ToraClassify> page = new Page<>(current,limit);
        List<ToraClassify> records = classifyService.getAllDeletedPage(page);
        long total = classifyService.selectDeletedCount();
        return Result.ok().data("records",records).data("total",total);
    }
}

