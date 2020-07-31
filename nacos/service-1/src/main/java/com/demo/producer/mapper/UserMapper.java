package com.demo.producer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.producer.entity.po.Permission;
import com.demo.producer.entity.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-07-29 16:15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 查询用户信息
    @Select(" select * from user where username = #{userName}")
    User findByUsername(@Param("userName") String userName);

    // 查询用户的权限
    @Select(" select permission.* from  user" + " inner join  user_role"
            + " on user.id = user_role.user_id inner join "
            + " role_permission on user_role.role_id = role_permission.role_id "
            + " inner join  permission on role_permission.perm_id = permission.id where user.username = #{userName};")
    List<Permission> findPermissionByUsername(@Param("userName") String userName);
}
