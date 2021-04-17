package com.wanghl.tora.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.tora.entity.ToraUser;
import com.wanghl.tora.entity.vo.LoginVo;
import com.wanghl.tora.entity.vo.RegisterVo;
import com.wanghl.tora.entity.vo.UserQueryVo;
import com.wanghl.tora.exception.MyException;
import com.wanghl.tora.mapper.ToraUserMapper;
import com.wanghl.tora.result.Result;
import com.wanghl.tora.result.ResultCode;
import com.wanghl.tora.service.ToraUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanghl.tora.utils.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
public class ToraUserServiceImpl extends ServiceImpl<ToraUserMapper, ToraUser> implements ToraUserService {

    //注册
    @Override
    public void register(RegisterVo user) {
        String username= user.getUsername().trim();
        String password= user.getPassword().trim();
        String email= user.getEmail().trim();
        String code= user.getCode().trim();
        //非空判断
        if (username.equals("") ||
                password.equals("") ||
                email.equals("") ||
                code.equals("")
        ){
            throw new MyException(ResultCode.ERROR,"信息不能为空");
        }
        //是否存在
        QueryWrapper<ToraUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        ToraUser one = baseMapper.selectOne(wrapper);
        if (one!=null){
            throw new MyException(ResultCode.ERROR,"用户名已存在");
        }
        ToraUser toraUser = new ToraUser();
        BeanUtils.copyProperties(user,toraUser);
        toraUser.setPassword(MD5.encrypt(password));
        toraUser.setAvatar("https://seikai-online-edu.oss-cn-beijing.aliyuncs.com/2021/03/14/777b73d9a15a48229418070379390a5307831.png");
        baseMapper.insert(toraUser);
    }

    //登录
    @Override
    public Result login(LoginVo user, HttpServletRequest request, HttpSession session) {
        String username = user.getUsername();
        String password = user.getPassword();
        String code = user.getCode();
        String captcha = (String) request.getSession().getAttribute("code");
        if (code.equals("")||!captcha.equalsIgnoreCase(code)){
            return Result.error().message("验证码错误");
        }
        //非空判断
        if (username.equals("") ||
                password.equals("")
        ){
            return Result.error().message("请输入用户名密码");
        }
        QueryWrapper<ToraUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        wrapper.eq("password",MD5.encrypt(password));
        ToraUser one = baseMapper.selectOne(wrapper);
        if (one == null){
            return Result.error().message("用户名或密码错误");
        } else {
            session.setAttribute("username",username);
            return Result.ok().data("username",username);
        }
    }

    //分页查询带条件
    @Override
    public List<ToraUser> queryUserPage(UserQueryVo userQueryVo, Page<ToraUser> page) {
        String username = userQueryVo.getUsername().trim();
        String email = userQueryVo.getEmail().trim();
        String begin = userQueryVo.getBegin().trim();
        String end = userQueryVo.getEnd().trim();
        QueryWrapper<ToraUser> wrapper = new QueryWrapper<>();
        if (!username.equals("")){
            wrapper.like("username",username);
        }
        if (!email.equals("")){
            wrapper.like("email",email);
        }
        if (!begin.equals("")){
            wrapper.like("gmt_create",begin);
        }
        if (!end.equals("")){
            wrapper.like("gmt_modified",end);
        }
        baseMapper.selectPage(page, wrapper);
        List<ToraUser> records = page.getRecords();
        return records;
    }

    @Override
    public ToraUser getUserInfo(String username) {
        QueryWrapper<ToraUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        ToraUser toraUser = baseMapper.selectOne(wrapper);
        return toraUser;
    }

}
