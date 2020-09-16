package com.shb.springsecurityjsonlogin.UserService.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shb.springsecurityjsonlogin.UserService.MenuService;
import com.shb.springsecurityjsonlogin.entity.Menu;
import com.shb.springsecurityjsonlogin.entity.Role;
import com.shb.springsecurityjsonlogin.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Shen Hanbo
 * @date : 2020/9/16 16:27
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAllMenu() {
        return menuMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public List<Role> getNeedRoleListById(Integer menuId) {
        return menuMapper.selectNeedRoleListById(menuId);
    }
}
