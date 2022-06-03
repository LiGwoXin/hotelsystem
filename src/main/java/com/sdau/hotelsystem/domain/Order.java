package com.sdau.hotelsystem.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName order
 */
@TableName(value ="order")
@Data
public class Order implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 房间id
     */
    private Integer roomId;
    @TableField(exist = false)
    private String room;
    /**
     * 会员id
     */
    private Integer memberId;

    @TableField(exist = false)
    private String member;

    /**
     * 入住时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date checkInTime;

    /**
     * 入住天数
     */
    private Integer days;

    /**
     * 退房时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date checkOutTime;

    /**
     * 顾客姓名
     */
    private String customersName;

    /**
     * 顾客人数
     */
    private Integer customersNum;

    /**
     * 电话号码
     */
    private String tel;

    /**
     * 价格
     */
    private BigDecimal totalPrice;

    /**
     * 操作人
     */
    private String changeUser;

    /**
     * 证件类型
     */
    private Byte cardType;

    /**
     * 证件号
     */
    private String cardNum;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 状态：0已入住，1已退房
     */
    private Byte status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}