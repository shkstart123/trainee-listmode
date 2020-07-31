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
 * @create 2020-07-30 20:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("role_permission")
public class RoleAndPermission {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField(value = "role_id")
    private String roleId ;
    @TableField(value = "perm_id")
    private String permId ;

}
