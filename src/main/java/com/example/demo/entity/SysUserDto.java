package com.example.demo.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.*;
import com.example.demo.converter.SexConverter;
import com.example.demo.converter.UserTypeConverter;
import com.example.demo.converter.WhetherConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description:
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.entity
 * @Author: Suellen
 * @CreateDate: 2022/1/19 14:15
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUser对象", description="用户表")
@ExcelIgnoreUnannotated //忽视无注解的属性
@ContentRowHeight(20) //文本高度
@HeadRowHeight(20) //标题高度
public class SysUserDto {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户主键")
    @TableId(value = "user_id", type = IdType.ASSIGN_UUID)
    @ExcelProperty(value = {"用户信息","序号"},index = 0)
    private String userId;

    @ApiModelProperty(value = "用户名称")
    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","姓名"},index = 1)
    private String name;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","账号"},index = 2)
    @ApiModelProperty(value = "用户账号")
    private String account;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","密码"},index = 3)
    @ApiModelProperty(value = "账号密码")
    private String password;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","手机"},index = 4)
    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","邮箱"},index = 5)
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","性别"},index = 6,converter = SexConverter.class)
    @ApiModelProperty(value = "用户性别(1:男;2:女)")
    private Integer gender;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","地址"},index = 7)
    @ApiModelProperty(value = "用户地址")
    private String address;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","类型"},index = 8,converter = UserTypeConverter.class)
    @ApiModelProperty(value = "用户类型")
    private Integer type;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","是否锁定"},index = 9,converter = WhetherConverter.class)
    @ApiModelProperty(value = "是否锁定(0:否;1:是)")
    private Integer isLock;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","创建时间"},index = 10)
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","修改时间"},index = 11)
    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ColumnWidth(30)
    @ExcelProperty(value = {"用户信息","最后修改密码时间"},index = 12)
    @ApiModelProperty(value = "最后修改密码时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastUpdatePasswordTime;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","是否删除"},index = 13,converter = WhetherConverter.class)
    @ApiModelProperty(value = "是否删除 0：否；1：是")
    @TableLogic
    private Integer deleted;
}
