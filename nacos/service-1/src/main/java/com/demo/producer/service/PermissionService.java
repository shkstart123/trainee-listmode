package com.demo.producer.service;

import com.demo.producer.entity.po.Permission;
import com.demo.producer.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-07-31 9:35
 */
@Service
public class PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    public int addPermission(Permission permission) {
        return permissionMapper.insert(permission);
    }

    public int deletePermission(int id) {
        return permissionMapper.deleteById(id);
    }

    public int updatePermission(Permission permission) {
        return permissionMapper.updateById(permission);
    }

    public Permission selectPermission(int id) {
        return permissionMapper.selectById(id);
    }
    public List<Permission> findAllPermission(){
        return  permissionMapper.findAllPermission();
    }
}
