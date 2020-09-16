package com.shb.springsecurityjsonlogin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shb.springsecurityjsonlogin.entity.Role;
import com.shb.springsecurityjsonlogin.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Shen Hanbo
 * @date : 2020/9/16 15:58
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    User loadUserByUsername(String username);

    List<Role> getRoleListByUserId(Integer userId);
}
