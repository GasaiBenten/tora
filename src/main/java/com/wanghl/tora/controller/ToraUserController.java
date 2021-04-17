package com.wanghl.tora.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wanghl.tora.entity.ToraUser;
import com.wanghl.tora.entity.vo.EditPassword;
import com.wanghl.tora.entity.vo.LoginVo;
import com.wanghl.tora.entity.vo.RegisterVo;
import com.wanghl.tora.entity.vo.UserQueryVo;
import com.wanghl.tora.result.Result;
import com.wanghl.tora.service.ToraUserService;
import com.wanghl.tora.utils.MD5;
import com.wanghl.tora.utils.VerifyCode;
import org.apache.catalina.session.StandardSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
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
@RequestMapping("/tora/user")
@CrossOrigin
public class ToraUserController {

    @Autowired
    private ToraUserService userService;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    //查询所有用户
    @GetMapping("getAllUser")
    public Result getAllUser(){
        List<ToraUser> userList = userService.list(null);
        return Result.ok().data("userList",userList);
    }

    //根据用户id查询用户
    @GetMapping("getByUserId/{id}")
    public Result getByUserId(@PathVariable String id){
        ToraUser toraUser = userService.getById(id);
        return Result.ok().data("user",toraUser);
    }

    //根据用户名获取用户信息
    @GetMapping("getUserInfo/{username}")
    public Result getUserInfo(@PathVariable String username){
        ToraUser user = userService.getUserInfo(username);
        user.setPassword(null);
        return Result.ok().data("user",user);
    }

    //查询所有用户带分页
    @GetMapping("userPage/{current}/{limit}")
    public Result userPage(@PathVariable long current,
                           @PathVariable long limit){
        Page<ToraUser> page = new Page<>(current,limit);
        userService.page(page,null);
        List<ToraUser> records = page.getRecords();
        return Result.ok().data("records",records);
    }

    //查询所有用户带分页带条件
    @PostMapping("queryUserPage/{current}/{limit}")
    public Result queryUserPage(@PathVariable long current,
                                @PathVariable long limit,
                                @RequestBody(required = false) UserQueryVo userQueryVo){
        Page<ToraUser> page = new Page<>(current,limit);
        List<ToraUser> records = userService.queryUserPage(userQueryVo,page);
        return Result.ok().data("records",records);
    }

    //注册
    @PostMapping("register")
    public Result register(@RequestBody RegisterVo user){
        userService.register(user);
        return Result.ok();
    }

    //登录
    @PostMapping("login")
    public Result login(@RequestBody LoginVo user,
                        HttpServletRequest request,
                        HttpSession session){
        Result result = userService.login(user, request, session);
        return result;
    }

    //注销
    @GetMapping("logout")
    public Result logout(HttpSession session){
        session.removeAttribute("user");
        return Result.ok();
    }

    //根据id删除用户
    @DeleteMapping("deleleUserById/{id}")
    public Result deleleUserById(@PathVariable String id){
        boolean flag = userService.removeById(Integer.parseInt(id));
        return flag ? Result.ok() : Result.error().message("删除失败");
    }

    //修改密码
    @PostMapping("changePassword")
    public Result changePassword(@RequestBody EditPassword editPassword){
        ToraUser toraUser = userService.getById(editPassword.getId());
        System.out.println(MD5.encrypt(editPassword.getOldPass()));
        System.out.println(toraUser.getPassword());
        if (!toraUser.getPassword().equals(MD5.encrypt(editPassword.getOldPass()))){
            return Result.error().message("旧密码错误");
        } else {
            toraUser.setPassword(MD5.encrypt(editPassword.getPass()));
            userService.updateById(toraUser);
        }
        return Result.ok();
    }


    //获取验证码图片
    @GetMapping("/getVerityCode")
    public void getVerityCode(HttpServletResponse response, HttpServletRequest request) {
        //定义response输出类型为image/jpeg
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response.setHeader("Cache-Control","post-check=0, pre-check=0");
        response.setHeader("Pragma","no-cache");
        response.setContentType("image/jpeg");

        //生成验证码
        //获取验证码文本内容
        String code = defaultKaptcha.createText();
        System.out.println("验证码内容："+code);
        //将验证码本文放入session
        request.getSession().setAttribute("code",code);
        //生成验证码图片
        BufferedImage image = defaultKaptcha.createImage(code);
        ServletOutputStream outputStream = null;
        try {
            outputStream= response.getOutputStream();
            //输出流出处文本图片，格式为jpg
            ImageIO.write(image,"jpg",outputStream);
            outputStream.flush();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (null!=outputStream){
                try {
                    outputStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}

