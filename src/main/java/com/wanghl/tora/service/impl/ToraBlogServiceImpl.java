package com.wanghl.tora.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.tora.entity.ToraBlog;
import com.wanghl.tora.entity.ToraBlogTag;
import com.wanghl.tora.entity.ToraTag;
import com.wanghl.tora.entity.ToraUser;
import com.wanghl.tora.entity.vo.BlogInfo;
import com.wanghl.tora.entity.vo.QueryBlogVo;
import com.wanghl.tora.mapper.ToraBlogMapper;
import com.wanghl.tora.mapper.ToraUserMapper;
import com.wanghl.tora.service.ToraBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanghl.tora.service.ToraBlogTagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanghl
 * @since 2021-03-26
 */
@Service
public class ToraBlogServiceImpl extends ServiceImpl<ToraBlogMapper, ToraBlog> implements ToraBlogService {

    @Autowired
    private ToraUserMapper userMapper;

    @Autowired
    private ToraBlogTagService blogTagService;

    @Autowired
    private ToraBlogMapper blogMapper;

    @Override
    public List<ToraBlog> queryBlogPage(Page<ToraBlog> page, QueryBlogVo blogVo) {

        String title = blogVo.getTitle().trim();
        String classify = blogVo.getClassify().trim();
        String tagId = blogVo.getTagId().trim();
        Integer status = blogVo.getStatus();
        String type = blogVo.getType().trim();
        String begin = blogVo.getBegin().trim();
        String end = blogVo.getEnd().trim();

        String username = blogVo.getUsername().trim();
        QueryWrapper<ToraUser> wrapper = new QueryWrapper<>();
        wrapper.like("username",username);
        wrapper.select("id");
        List<ToraUser> toraUser = userMapper.selectList(wrapper);
        Integer[] userIds = new Integer[toraUser.size()];
        for (int i = 0; i < toraUser.size(); i++) {
            userIds[i] = toraUser.get(i).getId();
        }

        QueryWrapper<ToraBlog> wrapper1 = new QueryWrapper<>();
        if (!title.equals("")){
            wrapper1.like("title",title);
        }
        if (!classify.equals("")){
            wrapper1.eq("classify",classify);
        }
        if (!tagId.equals("")){
            wrapper1.eq("tag_id",tagId);
        }
        if (status!=null){
            wrapper1.eq("t_publish",status);
        }
        if (!username.equals("")){
            wrapper1.like("username",username);
        }
        if (!type.equals("")){
            wrapper1.like("type",type);
        }
        if (!begin.equals("")){
            wrapper1.gt("gmt_create",begin);
        }
        if (!end.equals("")){
            wrapper1.lt("gmt_create",end);
        }
        List<ToraBlog> records = baseMapper.selectPage(page, wrapper1).getRecords();
        return records;
    }

    @Override
    public void addBlog(ToraBlog toraBlog, HttpSession session) {
        toraBlog.setUsername((String) session.getAttribute("username"));
        baseMapper.insert(toraBlog);

        if (toraBlog.getTagIds()!=null){
            Integer[] tagIds = toraBlog.getTagIds();
            ToraBlogTag blogTag = new ToraBlogTag();
            blogTag.setBlogId(toraBlog.getId());
            for (int i = 0; i < tagIds.length; i++) {
                blogTag.setTagId(tagIds[i]);
                blogTagService.save(blogTag);
            }
        }


    }



    @Override
    public List<ToraBlog> queryHistoryPage(Page<ToraBlog> page, QueryBlogVo blogVo) {
        String date = blogVo.getBegin();
        String title = blogVo.getTitle();
        String username = blogVo.getUsername();
        long size = page.getSize();
        long current = (page.getCurrent()-1)*size;
        List<ToraBlog> records = blogMapper.selectDeletedByQuery(current, size, date, title, username);
        return records;
    }

    @Override
    public void resumeById(String id) {
        baseMapper.resumeById(id);
    }

    @Override
    public List<ToraBlog> getHistoryPage(Page<ToraBlog> page) {
        long size = page.getSize();
        long current = (page.getCurrent() - 1) * size;
        List<ToraBlog> blogList = baseMapper.selectDeleted(current, size);
        return blogList;
    }

    @Override
    public ToraBlog getByBlogId(String id) {
        ToraBlog toraBlog = baseMapper.selectById(id);
        toraBlog.setTagList(blogTagService.getAllTagByBlogId(id));
        toraBlog.initTagIds();
        return toraBlog;
    }

    @Override
    public void updateBlogById(ToraBlog toraBlog) {
        baseMapper.updateById(toraBlog);
        Integer id = toraBlog.getId();
        QueryWrapper<ToraBlogTag> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id",id);
        blogTagService.remove(wrapper);

        if (toraBlog.getTagIds()!=null){
            Integer[] tagIds = toraBlog.getTagIds();
            ToraBlogTag blogTag = new ToraBlogTag();
            blogTag.setBlogId(toraBlog.getId());
            for (int i = 0; i < tagIds.length; i++) {
                blogTag.setTagId(tagIds[i]);
                blogTagService.save(blogTag);
            }
        }
    }

    @Override
    public long selectDeletedCount() {
        long total = baseMapper.selectDeletedCount();
        return total;
    }

    @Override
    public long selectCount() {
        Integer count = baseMapper.selectCount(null);
        return count;
    }

    @Override
    public void resumeBatchByIds(List idList) {
        for (int i = 0; i < idList.size(); i++) {
            baseMapper.resumeById(idList.get(i).toString());
        }
    }
}
