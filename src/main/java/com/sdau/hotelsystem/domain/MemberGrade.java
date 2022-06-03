package com.sdau.hotelsystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName member_grade
 */
@TableName(value ="member_grade")
@Data
public class MemberGrade implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 会员等级名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态：0可用 1禁用
     */
    private Byte status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}