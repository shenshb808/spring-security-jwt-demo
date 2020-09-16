package com.shb.springsecurityjsonlogin.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author : Shen Hanbo
 * @date : 2020/9/16 16:49
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * 校验登录用户是否拥有菜单所需角色
     * @param authentication 登录用户信息
     * @param object 获取当前请求的一些参数
     * @param configAttributes 上一步获取菜单所需角色的返回值
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //获取菜单需要角色
        for (ConfigAttribute attribute : configAttributes) {
            if("ROLE_login".equals(attribute.getAttribute())){
                //若请求地址没有匹配到任何菜单
                return;
            }
            //获取用户所有用的角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if(authority.getAuthority().equals(attribute.getAttribute())){
                    //若匹配上了
                    return;
                }
            }
        }
        throw new AccessDeniedException("非法请求");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
