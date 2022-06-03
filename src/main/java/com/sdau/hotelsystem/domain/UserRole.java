package com.sdau.hotelsystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户-角色关联表
 * @TableName user_role
 */
@TableName(value ="user_role")
@Data
@NoArgsConstructor
public class UserRole implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public UserRole(Integer userId, Integer roleId){
        this.roleId = roleId;
        this.userId = userId;
    }
}