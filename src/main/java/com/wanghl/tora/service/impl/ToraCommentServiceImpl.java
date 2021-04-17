package com.wanghl.tora.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.tora.entity.ToraComment;
import com.wanghl.tora.entity.vo.CommentQueryVo;
import com.wanghl.tora.entity.vo.CommentVo;
import com.wanghl.tora.mapper.ToraCommentMapper;
import com.wanghl.tora.service.ToraCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanghl.tora.service.ToraUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanghl
 * @since 2021-03-26
 */
@Service
public class ToraCommentServiceImpl extends ServiceImpl<ToraCommentMapper, ToraComment> implements ToraCommentService {

    @Autowired
    private ToraUserService userService;

    @Override
    public List<ToraComment> queryCommentPage(Page<ToraComment> page, CommentQueryVo commentQueryVo) {
        String title = commentQueryVo.getTitle();
        String keyWord = commentQueryVo.getKeyWord();
        String username = commentQueryVo.getUsername();
        Integer status = commentQueryVo.getStatus();
        String begin = commentQueryVo.getBegin();
        System.out.println(status);
        long size = page.getSize();
        long current = (page.getCurrent() - 1) * size;
        List<ToraComment> records = baseMapper.queryCommentPage(current, size, title, keyWord, username, status, begin);
        return records;
    }

    @Override
    public List<ToraComment> getCommentByBlogId(Page<ToraComment> page, String id) {

        //用于最终封装
        List<ToraComment> finalList = new ArrayList<>();
        //封装提出的所有子级回复
        List<ToraComment> children = new ArrayList<>();

        //一级评论
        long size = page.getSize();
        long current = (page.getCurrent() - 1) * size;
        List<ToraComment> nodes = baseMapper.selectComment(size, current, id);
        //二级评论
        List<ToraComment> roots = baseMapper.selectReply(id);
        //树状封装
        List<ToraComment> finalNodes = buildTree(nodes,roots);
        //改造树结构
        eachComment(finalNodes,finalList,children);
        return finalList;
    }

    private void eachComment(List<ToraComment> finalNodes, List<ToraComment> finalList, List<ToraComment> children) {
        for (int i = 0; i < finalNodes.size(); i++) {
            if (finalNodes.get(i).getLevel()==1){
                finalList.add(finalNodes.get(i));
                pickChildren(finalNodes.get(i),children);
                finalList.get(i).setChildren(children);
                children = new ArrayList<>();
            }
        }

    }

    private void pickChildren(ToraComment toraComment, List<ToraComment> children) {
        if (toraComment.getChildren().size()>0){
            for (ToraComment reply : toraComment.getChildren()){
                children.add(reply);
                pickChildren(reply, children);
            }
        }
    }


    private List<ToraComment> buildTree(List<ToraComment> nodes, List<ToraComment> roots) {

        List<ToraComment> finalList = new ArrayList<>();

        for (int i = 0; i < nodes.size(); i++) {
            ToraComment nodeComment = nodes.get(i);
            nodeComment.setLevel(1);
            finalList.add(selectChildren(nodeComment,roots));
        }

        return finalList;
    }

    private ToraComment selectChildren(ToraComment nodeComment, List<ToraComment> roots) {
        nodeComment.setChildren(new ArrayList<>());
        for (int j = 0; j < roots.size(); j++) {
            ToraComment rootComment = roots.get(j);
            if (nodeComment.getId().equals(rootComment.getParentId())){
                rootComment.setLevel(nodeComment.getLevel()+1);
                nodeComment.getChildren().add(selectChildren(rootComment,roots));
            }
        }
        return nodeComment;
    }


    @Override
    public void addComment(CommentVo comment, HttpSession session) {
        ToraComment toraComment = new ToraComment();
        BeanUtils.copyProperties(comment,toraComment);
        baseMapper.insert(toraComment);
    }

    @Override
    public void adopt(String id) {
        ToraComment toraComment = new ToraComment();
        toraComment.setId(Integer.parseInt(id));
        toraComment.setTPublish(1);
        baseMapper.updateById(toraComment);
    }

    @Override
    public boolean reject(String id) {
        Integer count = baseMapper.reject(id);
        return count == 0 ? false : true;
    }

    @Override
    public void adoptBatch(List ids) {
        for (int i = 0; i < ids.size(); i++) {
            baseMapper.adoptBatch(ids.get(i).toString());
        }
    }

    @Override
    public void resume(Integer id) {
        baseMapper.resume(id);
    }

    @Override
    public List<ToraComment> queryDeletedPage(Page<ToraComment> page, CommentQueryVo commentQueryVo) {
        String title = commentQueryVo.getTitle();
        String keyWord = commentQueryVo.getKeyWord();
        String username = commentQueryVo.getUsername();
        Integer status = commentQueryVo.getStatus();
        String begin = commentQueryVo.getBegin();

        long size = page.getSize();
        long current = (page.getCurrent() - 1) * size;
        List<ToraComment> records = baseMapper.queryDeletedPage(current, size, title, keyWord, username, status, begin);
        return records;
    }

    @Override
    public long selectDeletedCount() {
        Integer count = baseMapper.getDeletedCount();
        return count;
    }

    @Override
    public long selectBlogCommentCount(String id) {
        Integer total = baseMapper.selectBlogCommentCount(id);
        return total;
    }

    @Override
    public long selectCommentCount(Integer status) {
        return baseMapper.selectCommentCount(status);
    }


}
