package com.sdau.hotelsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sdau.hotelsystem.domain.*;
import com.sdau.hotelsystem.service.MenuService;
import com.sdau.hotelsystem.service.RoleMenuService;
import com.sdau.hotelsystem.service.UserRoleService;
import com.sdau.hotelsystem.util.ExcludeEmptyQueryWrapper;
import com.sdau.hotelsystem.util.Response;
import com.sdau.hotelsystem.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private HttpSession httpSession;

    /**
     * 初始化菜单
     * @return
     */
    @RequestMapping("init")
    public Map<String,Object> initMenu(){
        HashMap<String, Object> result = new HashMap<>();
        User token = (User) httpSession.getAttribute("currentUser");
        UserRole userRole = userRoleService.getOne(new ExcludeEmptyQueryWrapper<UserRole>()
                .eq("user_id", token.getId()));
        //获取菜单id
        List<Integer> menuIds =  roleMenuService.getByRoleId(userRole.getRoleId());
        //获取菜单
        List<Menu> menus = menuService.initMenu(menuIds);
        result.put("contentManagement",menus);
        return result;
    }

    /**
     *
     * @return
     */
    @RequestMapping("list")
    public Map<String,Object> list(Integer roleId){
        HashMap<String, Object> result = new HashMap<>();

        //获取菜单id
        List<Integer> menuIds =  roleMenuService.getByRoleId(roleId);
        List<Menu> menuList = menuService.list();
        //获取菜单
        List<Menu> menus = menuService.listMenu(menuList,menuIds);
        result.put("menu",menus);
        return result;
    }

    /**
     * 展示所有
     * @return
     */
    @PostMapping("all")
    public Response getAll(){
        List<Integer> menuIds = menuService.getIds();
        List<Menu> menus = menuService.initMenu(menuIds);
        return ResponseUtil.makeSuccess(menus);
    }

    /**
     * 展示所有
     * @return
     */
    @PostMapping("changePerm")
    public Response changePerm(@RequestParam("ids") Integer[] ids, @RequestParam("roleId") Integer roleId){

        roleMenuService.remove(new QueryWrapper<RoleMenu>()
        .eq("role_id",roleId)); //先删除 再添加

        List<RoleMenu> params = new ArrayList<>();
        for(Integer id : ids){
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(id);
            roleMenu.setRoleId(roleId);
            params.add(roleMenu);
        }
        roleMenuService.saveBatch(params);
        return ResponseUtil.makeSuccess();
    }
}
