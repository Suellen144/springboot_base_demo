package com.example.demo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhk
 * @since 2021-11-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name = "序号", width = 10)
    private Integer id;

    @Excel(name = "名字" ,orderNum = "0", width = 30)
    //@Excel(name = "性别", orderNum = "0",replace = { "男生_boy", "女生_girl" })
    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    @ApiModelProperty(value = "所属部门")
    @ExcelEntity(name = "所属部门", show = false)
    @TableField(exist = false)
    private Dept dept;

    @Excel(name = "年龄" ,orderNum = "3", width = 30)
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "手机" ,orderNum = "4", width = 30)
    @ApiModelProperty(value = "手机")
    private String phone;

    @Excel(name = "创建时间",format = "yyyy-MM-dd HH:mm:ss" ,orderNum = "5", width = 30)
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @Excel(name = "修改时间",format = "yyyy-MM-dd HH:mm:ss" ,orderNum = "6", width = 30)
    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除 0：否；1：是")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "版本号，乐观锁")
    @Version
    private Integer version;
}
