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
 * @create 2020-07-30 20:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_role")
public class UserAndRole {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField(value = "user_id")
    private String userId ;
    @TableField(value = "role_id")
    private String roleId ;

}
