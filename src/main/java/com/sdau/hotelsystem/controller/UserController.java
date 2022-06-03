package com.sdau.hotelsystem.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sdau.hotelsystem.domain.User;
import com.sdau.hotelsystem.domain.UserRole;
import com.sdau.hotelsystem.enums.ErrorCode;
import com.sdau.hotelsystem.service.UserRoleService;
import com.sdau.hotelsystem.service.UserService;
import com.sdau.hotelsystem.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("User")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 展示所有员工信息
     * @param param
     * @return
     */
    @RequestMapping("list")
    public PageInfo list(SearchInfo param){
        IPage<User> users = userService.list(param);
        List<User> res = users.getRecords();
        for (User user : res){
            String role = userService.getRole(user.getId());
            user.setRole(role);
        }
        return new PageInfo(users.getTotal(),res);
    }

    /**
     * 添加或编辑用户
     * @param user
     * @return
     */
    @RequestMapping("save")
    public Response save(User user,Integer roleId){
        if(user.getId()==null){//添加
            String random = SaltUtils.getSalt(20); //生成随机字符串
            ByteSource salt = ByteSource.Util.bytes(random);
            // 默认密码 用户名
            user.setSalt(random);
            user.setUserpass(user.getUsername());
            String newPassword = new SimpleHash("MD5", user.getUserpass(), salt, 1024).toHex();
            user.setUserpass(newPassword);
            userService.save(user);
            userRoleService.save(new UserRole(user.getId(),roleId));
            return ResponseUtil.makeSuccess(null,"添加成功！");
        }else{//编辑
            userService.updateById(user);
            userRoleService.updateById(new UserRole(user.getId(),roleId));
            return ResponseUtil.makeSuccess(null,"编辑成功！");
        }
    }
    /**
     * 修改个人信息
     * @param user
     * @return
     */
    @PostMapping("change")
    public Response changeInfo(User user){
            userService.updateById(user);
            return ResponseUtil.makeSuccess(null,"修改成功！");
    }

    /**
     * 判断密码是否正确
     * @param password
     * @return
     */
    @RequestMapping("isTrue")
    public Response isTrue(String password){
        User token = (User)session.getAttribute("currentUser");
        User user = userService.getById(token.getId());
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());
        String old_password = new SimpleHash("MD5", password, salt, 1024).toHex();
        // 校验传入的密码，是否等于数据库中的密码
        if (user.getUserpass().equals(old_password)) {
            return ResponseUtil.makeSuccess();
        }
        return ResponseUtil.makeFail();
    }

    /**
     *修改密码
     * @param newPwd
     * @return
     */
    @PostMapping("changePwd")
    public Response changePwd(String newPwd){
        User token = (User)session.getAttribute("currentUser");
        User user = userService.getById(token.getId());
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());
        String new_password = new SimpleHash("MD5", newPwd, salt, 1024).toHex();
            userService.update(new UpdateWrapper<User>()
                    .set("userpass",new_password)
                    .eq("id",user.getId()));
            return ResponseUtil.makeSuccess();
    }

    /**
     * 逻辑删除，即离职禁止登陆
     * @param id
     * @return
     */
    @RequestMapping("del")
    public Response delete(Integer id){
        userService.update(new UpdateWrapper<User>()
                .set("status",1)
                .eq("id",id));
        return ResponseUtil.makeSuccess();
    }

    /**
     * 批量注销
     * @param ids
     * @return
     */
    @PostMapping("dels")
    public Response delete(@RequestBody List<Integer> ids){
        for(Integer id:ids){
            userService.update(new UpdateWrapper<User>()
                    .set("status",1)
                    .eq("id",id));
        }
        return ResponseUtil.makeSuccess();
    }

    /**
     * 判断用户是否存在
     * @param username
     * @return
     */
    @RequestMapping("isExist")
    public Response isExist(String username){
        User result = userService.getByUserName(username);
        if(result==null){
            return ResponseUtil.makeSuccess();
        }else{
            return ResponseUtil.makeFail();
        }
    }

    /**
     *初始信息
     * @param
     * @return
     */
    @RequestMapping("init")
    public Response init(){
        User token = (User)session.getAttribute("currentUser");
        User result = userService.getOneById(token.getId());
        return ResponseUtil.makeSuccess(result);
    }
}
