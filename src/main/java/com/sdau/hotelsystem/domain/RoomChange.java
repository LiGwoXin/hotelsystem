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
 * @TableName room_change
 */
@TableName(value ="room_change")
@Data
public class RoomChange implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单号
     */
    private Integer orderId;

    /**
     * 更改前房间
     */
    private String originalRoom;

    /**
     * 更改后房间
     */
    private String alterRoom;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作人
     */
    private String changeUser;

    /**
     * 更换时间
     */
    private Date createTime;

    /**
     * 状态：0入住中，1已退房
     */
    private Byte status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}