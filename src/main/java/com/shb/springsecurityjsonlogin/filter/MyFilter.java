package com.shb.springsecurityjsonlogin.filter;

import com.shb.springsecurityjsonlogin.UserService.MenuService;
import com.shb.springsecurityjsonlogin.entity.Menu;
import com.shb.springsecurityjsonlogin.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 拦截请求路径,解析请求路径所需的角色,将所需角色提供给Security
 * @author : Shen Hanbo
 * @date : 2020/9/16 16:20
 */
@Component
public class MyFilter implements FilterInvocationSecurityMetadataSource {

    //Ant路径匹配器
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private MenuService menuService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取请求路径
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        //获取所有菜单并且遍历, 看哪个菜单匹配上
        List<Menu> menuList = menuService.getAllMenu();
        for (Menu menu : menuList) {
            if(antPathMatcher.match(menu.getPattern(),requestUrl)){
                //若匹配上了,获取菜单所需角色
                List<Role> roles = menuService.getNeedRoleListById(menu.getId());
                String[] roleStr = new String[roles.size()];
                for (int i = 0; i < roleStr.length; i++) {
                    roleStr[i] = roles.get(i).getName();
                }
                List<ConfigAttribute> list = SecurityConfig.createList(roleStr);
                return list;
            }
        }

        //假如请求路径没匹配上任何菜单,添加任意标记符
        return SecurityConfig.createList("ROLE_login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
