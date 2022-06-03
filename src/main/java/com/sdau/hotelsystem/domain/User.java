package com.sdau.hotelsystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 标识符，主键，自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录密码
     */
    private String userpass;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 状态： 0在职 ，1离职
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 操作人
     */
    private String changeUser;

    /**
     * 电话号码
     */
    private String tel;

    /**
     * 所属部门
     */
    private Integer departmentId;

    @TableField(exist = false)
    private String department;

    @TableField(exist = false)
    private String role;
    /**
     * 性别：0女，1男
     */
    private Byte sex;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 头像
     */
    private String img;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}