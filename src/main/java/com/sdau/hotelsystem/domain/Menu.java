package com.sdau.hotelsystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName menu
 */
@TableName(value ="menu")
@Data
public class Menu implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单名
     */
    private String title;

    /**
     * 图标
     */
    private String icon;

    /**
     * 链接
     */
    private String href;

    /**
     * 是否可以展开：0不可以，1可以
     */
    private Byte spread;

    /**
     * 是否有子菜单：0无，1有
     */
    private Byte hasChild;

    /**
     * 父菜单id
     */
    private Integer parentId;

    /**
     * 排序准则
     */
    private Byte sort;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<Menu> children;

    @TableField(exist = false)
    private String checked;


}