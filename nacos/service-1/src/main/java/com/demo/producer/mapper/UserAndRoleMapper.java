package com.demo.producer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.producer.entity.po.UserAndRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author shkstart
 * @create 2020-07-31 9:33
 */
@Mapper
public interface UserAndRoleMapper extends BaseMapper<UserAndRole> {
}
