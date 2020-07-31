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
 * @create 2020-07-29 16:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("permission")
public class Permission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    // 权限名称
    @TableField(value = "permName")
    private String permName;
    // 权限标识
    @TableField(value = "permTag")
    private String permTag;
    // 请求url
    @TableField(value = "url")
    private String url;
}
