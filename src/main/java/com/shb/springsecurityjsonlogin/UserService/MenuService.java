package com.shb.springsecurityjsonlogin.UserService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shb.springsecurityjsonlogin.entity.Menu;
import com.shb.springsecurityjsonlogin.entity.Role;

import java.util.List;

/**
 * @author : Shen Hanbo
 * @date : 2020/9/16 16:27
 */
public interface MenuService extends IService<Menu> {

    List<Menu> getAllMenu();

    List<Role> getNeedRoleListById(Integer menuId);
}
