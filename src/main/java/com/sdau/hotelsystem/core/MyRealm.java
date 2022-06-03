package com.sdau.hotelsystem.core;

import com.sdau.hotelsystem.domain.User;
import com.sdau.hotelsystem.service.LoginService;
import com.sdau.hotelsystem.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * 自定义域
 *
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    /**
     * 认证，出现异常会被ControllerExceptionHandler捕获
     *
     * @param token 用户token
     * @return 认证信息
     * @throws AuthenticationException 认证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        String password_login = new String((char[]) token.getCredentials());
        User user = userService.getByUserName(userName);
        System.out.println(user.getUserpass());
        System.out.println(password_login);
        if (Objects.isNull(user)) {
            throw new UnknownAccountException("账号不存在！");
        } else if (user.getStatus().equals(1)) {
            throw new UnknownAccountException("该用户已经被锁定！");
        } else {
            ByteSource salt = ByteSource.Util.bytes(user.getSalt());
            String password = new SimpleHash("MD5", password_login, salt, 1024).toHex();
            // 校验传入的密码，是否等于数据库中的密码
            if (user.getUserpass().equals(password)) {
//                AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getUserpass(), user.getUsername());
                AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,user.getUserpass(),salt,getName());
                // 将user对象放到session属性中
                SecurityUtils.getSubject().getSession().setAttribute("currentUser", user);
                return authenticationInfo;
            } else {
                throw new IncorrectCredentialsException("密码错误！");
            }
//            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());//盐值
//            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,user.getUserpass(),credentialsSalt,getName());
//            // 将user对象放到session属性中
//            SecurityUtils.getSubject().getSession().setAttribute("token", user);
//            return authenticationInfo;
        }
    }

    /**
     * 授权，出现异常会被ControllerExceptionHandler捕获
     *
     * @param principals shiro框架中用户信息对象
     * @return 授权信息
     * @throws AuthorizationException 授权异常
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.getPrimaryPrincipal();
        // 根据用户名授予相应的权限，用户名需唯一
        return loginService.getRolesAndPermissionsByUserName(userName);
    }
}
