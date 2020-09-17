package com.shb.springsecurityjsonlogin.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 请求校验JWT token的有效性
 * @author : Shen Hanbo
 * @date : 2020/9/17 10:48
 */
public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        //请求头取token
        String jwtToken = request.getHeader("authorization");
        //解析Token
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey("shenhanbo123")
                .parseClaimsJws(jwtToken.replace("Bearer", ""));
        //获取Token里面的参数,这些东西都是我们生层Token的时候放进去的
        Claims body = jws.getBody();
        String username = body.getSubject();
        String authorities = (String)body.get("authorities");
        //工具类角色字符串解析,生成的时候我们是逗号分隔权限的
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

        //生成认证的Authentication
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(usernamePasswordAuthenticationToken);

        //过滤器继续往下走
        filterChain.doFilter(servletRequest,servletResponse);

    }
}
