package com.demo.producer.service;

import com.demo.producer.entity.po.Role;
import com.demo.producer.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shkstart
 * @create 2020-07-31 9:15
 */
@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RoleAndPermissionService roleAndPermissionService;
    public int insertRole(Role role){
        return roleMapper.insert(role);
    }
    public int deleteRole(int id){
        roleAndPermissionService.deleteByRoleId(id);
        return roleMapper.deleteById(id);
    }
    public int updateRole(Role role){
        return roleMapper.updateById(role);
    }
    public Role selectRole(int id){
        return roleMapper.selectById(id);
    }



}
