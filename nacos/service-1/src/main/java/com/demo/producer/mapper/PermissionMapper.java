package com.demo.producer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.producer.entity.po.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-07-29 16:33
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    // 查询所有权限
    @Select(" select * from permission ")
    List<Permission> findAllPermission();
}
