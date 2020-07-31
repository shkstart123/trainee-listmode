package com.demo.producer.service;

import com.demo.producer.entity.po.UserAndRole;
import com.demo.producer.mapper.UserAndRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author shkstart
 * @create 2020-07-31 10:07
 */
@Service
public class UserAndRoleService {
    @Autowired
    UserAndRoleMapper userAndRoleMapper;
    public int insertUserAndRole(UserAndRole userAndRole){
      return  userAndRoleMapper.insert(userAndRole);
    }
    public int deleteUserAndRole(int id){
        return  userAndRoleMapper.deleteById(id);
    }
    public int deleteByUid(int uid){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("user_id",uid);
        return  userAndRoleMapper.deleteByMap(hashMap);
    }
    public int updateUserAndRole(UserAndRole userAndRole){
        return  userAndRoleMapper.updateById(userAndRole);
    }
    public UserAndRole selectUserAndRole(int id){
        return  userAndRoleMapper.selectById(id);
    }



}
