package com.wanghl.tora.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.tora.entity.ToraUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanghl.tora.entity.vo.LoginVo;
import com.wanghl.tora.entity.vo.RegisterVo;
import com.wanghl.tora.entity.vo.UserQueryVo;
import com.wanghl.tora.result.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanghl
 * @since 2021-03-26
 */
public interface ToraUserService extends IService<ToraUser> {

    void register(RegisterVo user);

    Result login(LoginVo user, HttpServletRequest request, HttpSession session);

    List<ToraUser> queryUserPage(UserQueryVo userQueryVo, Page<ToraUser> page);

    ToraUser getUserInfo(String username);
}
