package com.demo.producer.service;

import com.demo.producer.entity.po.RoleAndPermission;
import com.demo.producer.mapper.RoleAndPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author shkstart
 * @create 2020-07-31 9:45
 */
@Service
public class RoleAndPermissionService {
    @Autowired
    RoleAndPermissionMapper roleAndPermissionMapper;
    public int addRoleAndPermission(RoleAndPermission roleAndPermission){
        return roleAndPermissionMapper.insert(roleAndPermission);
    }
    public int deleteRoleAndPermission(int id){
        return roleAndPermissionMapper.deleteById(id);
    }
    public int updateRoleAndPermission(RoleAndPermission roleAndPermission){
        return roleAndPermissionMapper.updateById(roleAndPermission);
    }
    public RoleAndPermission selectRoleAndPermission(int id){
        return roleAndPermissionMapper.selectById(id);
    }

    public int deleteByRoleId(int rid){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("role_id",rid);
        return roleAndPermissionMapper.deleteByMap(hashMap);
    }
}
