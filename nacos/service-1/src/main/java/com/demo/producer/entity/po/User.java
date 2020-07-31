package com.demo.producer.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-07-29 15:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User implements UserDetails {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField(value = "username")
    private String username ;
    @TableField(value = "password")
    private String password ;
    // true: 账号未过期.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // true: 账号未锁定.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // true: 凭证未过期.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // true: 账号可用.
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 用户所有权限
    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }



}
