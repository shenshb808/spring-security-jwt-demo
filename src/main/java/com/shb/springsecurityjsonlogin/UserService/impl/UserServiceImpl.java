package com.shb.springsecurityjsonlogin.UserService.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shb.springsecurityjsonlogin.UserService.UserService;
import com.shb.springsecurityjsonlogin.entity.User;
import com.shb.springsecurityjsonlogin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author : Shen Hanbo
 * @date : 2020/9/16 15:51
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在!");
        }
        user.setRoleList(userMapper.getRoleListByUserId(user.getId()));
        return user;
    }

}
