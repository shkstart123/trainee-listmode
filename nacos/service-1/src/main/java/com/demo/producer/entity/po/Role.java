package com.demo.producer.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shkstart
 * @create 2020-07-29 16:11
 */

// 角色信息表
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("role")
public class Role {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(value = "roleName")
    private String roleName;
    @TableField(value = "roleDesc")
    private String roleDesc;
}
