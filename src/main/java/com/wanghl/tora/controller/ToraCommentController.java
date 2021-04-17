package com.wanghl.tora.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.tora.entity.ToraComment;
import com.wanghl.tora.entity.vo.CommentQueryVo;
import com.wanghl.tora.entity.vo.CommentVo;
import com.wanghl.tora.result.Result;
import com.wanghl.tora.service.ToraCommentService;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
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
@RequestMapping("/tora/comment")
@CrossOrigin
public class ToraCommentController {

    @Autowired
    private ToraCommentService commentService;

    //查询所有评论
    @GetMapping("getAllComment")
    public Result getAllComment(){
        List<ToraComment> commentList = commentService.list(null);
        return Result.ok().data("commentList",commentList);
    }

    //查询所有评论带分页
    @GetMapping("getCommentPage/{current}/{limit}")
    public Result getCommentPage(@PathVariable long current,
                                 @PathVariable long limit){
        Page<ToraComment> page = new Page<>(current,limit);
        List<ToraComment> records = commentService.page(page, null).getRecords();
        long total = commentService.page(page, null).getTotal();
        return Result.ok().data("records",records).data("total",total);
    }

    //查询所有评论带分页带条件
    @PostMapping("queryCommentPage/{current}/{limit}")
    public Result queryCommentPage(@PathVariable long current,
                                   @PathVariable long limit,
                                   @RequestBody CommentQueryVo commentQueryVo){
        Page<ToraComment> page = new Page<>(current,limit);
        List<ToraComment> records = commentService.queryCommentPage(page, commentQueryVo);
        if (records.size() == 0){
            return Result.error().message("无查询结果");
        }
        long total = commentService.selectCommentCount(commentQueryVo.getStatus());
        return Result.ok().data("records",records).data("total",total);
    }

    //根据博客id查询评论带分页（树状）
    @GetMapping("getCommentByBlogId/{id}/{current}/{limit}")
    public Result getCommentByBlogId(@PathVariable long current,
                                     @PathVariable long limit,
                                     @PathVariable String id){
        Page<ToraComment> page = new Page<>(current,limit);
        List<ToraComment> records = commentService.getCommentByBlogId(page,id);
        if (records.size() == 0){
            return Result.error().message("无查询结果");
        }
        long total = commentService.selectBlogCommentCount(id);

        return Result.ok().data("records",records).data("total",total);
    }

    //添加评论
    @PostMapping("addComment")
    public Result addComment(@RequestBody CommentVo comment,
                             HttpSession session){
        commentService.addComment(comment,session);
        return Result.ok();
    }

    //通过评论
    @GetMapping("adopt/{id}")
    public Result adopt(@PathVariable String id){
        commentService.adopt(id);
        return Result.ok();
    }

    //驳回评论
    @GetMapping("reject/{id}")
    public Result reject(@PathVariable String id){
        boolean flag = commentService.reject(id);
        return flag ? Result.ok() : Result.error().message("删除失败");
    }

    //删除评论
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable Integer id){
        boolean flag = commentService.removeById(id);
        return flag ? Result.ok() : Result.error().message("删除失败");
    }

    //恢复评论
    @GetMapping("resume/{id}")
    public Result resume(@PathVariable Integer id){
        commentService.resume(id);
        return Result.ok();
    }

    //批量同意
    @PostMapping("adoptBatch")
    public Result adoptBatch(@RequestBody List<ToraComment> selection){
        List ids = new ArrayList();
        for (int i = 0; i < selection.size(); i++) {
            ids.add(selection.get(i).getId());
        }
        commentService.adoptBatch(ids);
        return Result.ok();
    }

    //批量驳回
    @PostMapping("rejectBatch")
    public Result rejectBatch(@RequestBody List<ToraComment> selection){

        for (int i = 0; i < selection.size(); i++) {
            commentService.reject(selection.get(i).getId().toString());
        }

        return Result.ok();
    }

    //批量恢复
    @PostMapping("resumeBatch")
    public Result resumeBatch(@RequestBody List<ToraComment> selection){

        for (int i = 0; i < selection.size(); i++) {
            commentService.resume(selection.get(i).getId());
        }

        return Result.ok();
    }

    @PostMapping("queryDeletedPage/{current}/{limit}")
    public Result queryDeletedPage(@PathVariable long current,
                                   @PathVariable long limit,
                                   @RequestBody CommentQueryVo  commentQueryVo){
        Page<ToraComment> page = new Page<>(current,limit);
        List<ToraComment> records = commentService.queryDeletedPage(page,commentQueryVo);
        long total = commentService.selectDeletedCount();
        return Result.ok().data("records",records).data("total",total);
    }
}

