package com.sdau.hotelsystem.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.sdau.hotelsystem.core.RedisSessionDAO;
import com.sdau.hotelsystem.domain.*;
import com.sdau.hotelsystem.service.*;
import com.sdau.hotelsystem.util.ExcludeEmptyQueryWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * TbUserServiceImpl
 *
 */

@Transactional
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Value("${shiro.redis.sessionPrefix}")
    private String sessionPrefix;
    @Value("${shiro.redis.verificationCodeTime}")
    private Long verificationCodeTime;
    @Value("${shiro.redis.kickOutKey}")
    private String kickOutKey;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    DefaultKaptcha producer;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RolePermService rolePermService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private RedisSessionDAO redisSessionDAO;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;

    @Override
    public User getUserByUserName(String userName) {
        return userService.getByUserName(userName);
//        ExcludeEmptyQueryWrapper<User> queryWrapper = new ExcludeEmptyQueryWrapper<>();
//        queryWrapper.eq("username",userName);
//        return userService.getOne(queryWrapper);
    }

    @Override
    public SimpleAuthorizationInfo getRolesAndPermissionsByUserName(String userName) {
        // 查询用户信息
        ExcludeEmptyQueryWrapper<User> queryWrapper = new ExcludeEmptyQueryWrapper<>();
        queryWrapper.eq("username",userName);
        User user =  userService.getByUserName(userName);

        if (Objects.isNull(user)) {
            return new SimpleAuthorizationInfo();
        }

        // 查询用户-角色关联信息
//        List<UserRole> userRoleList = userRoleService.getByUserId(user.getId());
        List<UserRole> userRoleList = userRoleService.list(new ExcludeEmptyQueryWrapper<UserRole>()
                .eq("user_id",user.getId()));
        if (CollectionUtils.isEmpty(userRoleList)) {
            return new SimpleAuthorizationInfo();
        }
        List<Integer> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(roleIds)) {
            return new SimpleAuthorizationInfo();
        }
        // 根据角色id集合，查询角色信息
        List<Role> roleList = roleService.getByIds(roleIds);
        if (CollectionUtils.isEmpty(roleList)) {
            return new SimpleAuthorizationInfo();
        }
        Set<String> roleNameSet = roleList.stream().map(Role::getName).collect(Collectors.toSet());
        // 根据角色id集合，查询角色-权限关联数据
        List<RolePerm> rolePermissionList = rolePermService.getByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(rolePermissionList)) {
            return new SimpleAuthorizationInfo();
        }
        List<Integer> permissionIds = rolePermissionList.stream().map(RolePerm::getPermId).collect(Collectors.toList());
        // 查询权限数据集合
        List<Permission> permissionList = permissionService.getByPermissionIds(permissionIds);
        if (CollectionUtils.isEmpty(permissionList)) {
            return new SimpleAuthorizationInfo();
        }
        Set<String> permissionNameSet = permissionList.stream().map(Permission::getName).collect(Collectors.toSet());
        // 配置角色、权限
        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
        auth.setRoles(roleNameSet);
        auth.setStringPermissions(permissionNameSet);
        return auth;
    }

    @Override
    public boolean checkCodeToken(String sToken, String textStr) {
        Object value = redisTemplate.opsForValue().get(sToken);
        return textStr.equals(value);
    }

    @Override
    public Map<String, Object> generateVerificationCode() throws Exception {
        Map<String, Object> map = new HashMap<>();
        // 生成文字验证码
        String text = producer.createText();
        System.out.println("图片验证码为：" + text);
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        // 转成Base64位字符串
        map.put("img", Base64.getEncoder().encodeToString(outputStream.toByteArray()));
        // 生成验证码对应的token  以token为key  验证码为value存在redis中
        String codeToken = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(codeToken, text, verificationCodeTime, TimeUnit.MINUTES);
        map.put("cToken", codeToken);
        return map;
    }

    @Override
    public List<User> listOnLineUser() {
        Set setSessionIds = redisTemplate.keys(sessionPrefix + "*");
        List list = new ArrayList<User>(setSessionIds.size());
        Iterator<String> iter = setSessionIds.iterator();
        while (iter.hasNext()) {
            String temp = iter.next();
            SimpleSession session = (SimpleSession) redisTemplate.opsForValue().get(temp);
            System.out.println("用户信息：" + session.getAttribute("currentUser"));
            list.add(session.getAttribute("currentUser"));
        }
        return list;
    }

    @Override
    public boolean removeSessionBySessionId(String sessionId) {
        String key = sessionPrefix + sessionId;
        return redisTemplate.delete(key);
    }

    @Override
    public boolean forbiddenByUserName(String userName) {
        List<Session> outCacheSessions = getSessionByUsername(userName);
        for (Session outCacheSession : outCacheSessions) {
            DefaultSessionKey defaultSessionKey = new DefaultSessionKey(outCacheSession.getId());
            Session outSession = sessionManager.getSession(defaultSessionKey);
            // 设置会话的out属性表示踢出了
            if (outSession != null) {
                outSession.setAttribute(kickOutKey, true);
                // 除了强制下线外，将账号设为禁用，禁止再次登录
                userService.banned(userName);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public List<Menu> getMenuByUserName(String userName) {
        List<Menu> menuList = new ArrayList<>();
        // 根据用户名称，查询用户信息
//        User user = userService.getByUserName(userName);
        User user = userService.getOne(new ExcludeEmptyQueryWrapper<User>().eq("username",userName));
        if (Objects.isNull(user)) {
            return menuList;
        }
        // 根据用户id查询角色
        List<UserRole> userRoleList = userRoleService.getByUserId(user.getId());
        List<Integer> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(roleIds)) {
            List<RoleMenu> roleMenuList = roleMenuService.getByRoleIds(roleIds);
            List<Integer> menuIds = roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(menuIds)) {
                menuList = menuService.getByIds(menuIds);
            }
        }
        return menuList;
    }

    /**
     * 根据用户名，查询session集合
     *
     * @param username 用户名
     * @return session集合
     */
    public List<Session> getSessionByUsername(String username){
        Collection<Session> allSessions = redisSessionDAO.getActiveSessions();
        List<Session> sessions = new ArrayList<>();
        for(Session session : allSessions){
            if(null != session && String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)).equals(username)){
                sessions.add(session);
            }
        }
        return sessions;
    }
}
