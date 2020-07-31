package com.demo.producer.service;

import com.demo.producer.entity.po.Permission;
import com.demo.producer.entity.po.User;
import com.demo.producer.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-07-29 16:13
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAndRoleService userAndRoleService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            // 1.根据用户名称查询数据用户信息
            User user = userMapper.findByUsername(username);
            // 2.底层会根据数据库查询用户信息，判断密码是否正确
            // 3.给用户设置权限
            List<Permission> listPermission = userMapper.findPermissionByUsername(username);
            if (listPermission != null && listPermission.size() > 0) {
                // 定义用户权限
                List<GrantedAuthority> authorities = new ArrayList<>();
                for (Permission permission : listPermission) {
                    authorities.add(new SimpleGrantedAuthority(permission.getPermTag()));
                }
                user.setAuthorities(authorities);
            }
            return user;
    }

    public int addUser(User user){
        return userMapper.insert(user);
    }

    public int deleteUser(int id){
        return userMapper.deleteById(id);
    }

    public int updateUser(User user){
        return userMapper.updateById(user);
    }
    public User selectUser(int id){
        return userMapper.selectById(id);
    }





}
