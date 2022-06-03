package com.sdau.hotelsystem.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName reservation
 */
@TableName(value ="reservation")
@Data
public class Reservation implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 外键房间id
     */
    private Integer roomId;

    @TableField(exist = false)
    private String room;

    /**
     * 电话号码
     */
    private String tel;

    /**
     * 客人姓名
     */
    private String customersName;

    /**
     * 人数
     */
    private Integer customersNum;

    /**
     * 入住日期
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date checkInTime;

    /**
     * 退房日期
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date checkOutTime;

    /**
     * 预定时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 状态：0预定中，1已入住，2已退房，3已取消
     */
    private Byte status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 会员id
     */
    private Integer memberId;

    @TableField(exist = false)
    private String member;

    /**
     * 操作人
     */
    private String changeUser;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}