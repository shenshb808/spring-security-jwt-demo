package com.shb.springsecurityjsonlogin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shb.springsecurityjsonlogin.entity.Menu;
import com.shb.springsecurityjsonlogin.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Shen Hanbo
 * @date : 2020/9/16 16:26
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    List<Role> selectNeedRoleListById(Integer menuId);
}
