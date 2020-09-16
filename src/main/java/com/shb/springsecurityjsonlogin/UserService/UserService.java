package com.shb.springsecurityjsonlogin.UserService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shb.springsecurityjsonlogin.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author : Shen Hanbo
 * @date : 2020/9/16 15:50
 */
public interface UserService extends UserDetailsService, IService<User> {

}
