package com.shb.springsecurityjsonlogin.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shb.springsecurityjsonlogin.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录认证
 * @author : Shen Hanbo
 * @date : 2020/9/17 9:57
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    /**
     * 认证
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        //获取JSON形式的User对象
        User user = new ObjectMapper().readValue(request.getInputStream(),User.class);
        //获取AuthenticationManager,用于校验
        AuthenticationManager authenticationManager = getAuthenticationManager();
        //生成Authentication对象
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        //校验Authentication,返回一个认证完成后的Authentication对象。
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return authenticate;
    }

    /**
     * 登陆成功回调, 返回JWT信息
     * @param request
     * @param response
     * @param chain
     * @param authResult 登录成功后的登录信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //获取登录用户角色
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        //将角色返回成字符串返回到前端
        StringBuffer stringBuffer = new StringBuffer();
        for (GrantedAuthority authority : authorities) {
            stringBuffer.append(authority.getAuthority()).append(",");
        }
        //生成JWT
        String jwtToken = Jwts.builder()
                .claim("authorities", stringBuffer)//角色
                .setSubject(authResult.getName())//主题
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))//过期时间
                .signWith(SignatureAlgorithm.HS512, "shenhanbo123")//签名算法以及签名key
                .compact();
        Map<String,Object> map = new HashMap<>();
        map.put("token",jwtToken);
        map.put("msg","登录成功");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(new ObjectMapper().writeValueAsString(map));
        printWriter.flush();
        printWriter.close();
    }

    /**
     * 登录失败回调
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String,Object> map = new HashMap<>();
        map.put("msg","登录失败" + failed.getMessage());
        response.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(new ObjectMapper().writeValueAsString(map));
        printWriter.flush();
        printWriter.close();
    }
}
