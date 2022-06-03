package com.sdau.hotelsystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName room_status
 */
@TableName(value ="room_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomStatus implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 房间id
     */
    private Integer roomId;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GTM+8")
    private Date time;

    /**
     * 状态： 0预约，1入住
     */
    private Byte status;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 预约id
     */
    private Integer reservationId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public RoomStatus(Integer roomId, Integer reservationId, Byte status){
        this.roomId = roomId;
        this.reservationId = reservationId;
        this.status = status;
    }
}