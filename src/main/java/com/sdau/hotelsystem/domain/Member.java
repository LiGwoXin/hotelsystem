package com.sdau.hotelsystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName member
 */
@TableName(value ="member")
@Data
public class Member implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 会员卡号
     */
    private Integer num;

    /**
     * 姓名
     */
    private String name;

    /**
     * 电话号
     */
    private String tel;

    /**
     * 会员等级
     */
    private Integer gradeId;

    @TableField(exist = false)
    private String grade;

    /**
     * 会员状态: 0可用，1禁用
     */
    private Byte status;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 操作人
     */
    private String changeUser;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}