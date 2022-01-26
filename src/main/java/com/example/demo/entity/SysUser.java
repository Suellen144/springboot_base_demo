package com.example.demo.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.*;
import com.example.demo.common.ValidatedGroups;
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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zhk
 * @since 2021-12-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUser对象", description="用户表")
@ExcelIgnoreUnannotated //忽视无注解的属性
@ContentRowHeight(20) //文本高度
@HeadRowHeight(20) //标题高度
public class SysUser implements Serializable {

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
    @NotEmpty(message = "用户账号不能为空", groups = {ValidatedGroups.login.class})
    private String account;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","密码"},index = 3)
    @ApiModelProperty(value = "账号密码")
    @NotEmpty(message = "账号密码不能为空", groups = {ValidatedGroups.login.class})
    private String password;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","手机"},index = 4)
    @ApiModelProperty(value = "手机号码")
    @NotEmpty(message = "手机号码不能为空")
    @Pattern(regexp = "^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$",message ="手机格式有误")
    private String phone;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","邮箱"},index = 5)
    @ApiModelProperty(value = "用户邮箱")
    @NotNull(message = "用户邮箱不能为空")
    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",message = "邮箱格式有误")
    private String email;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","性别"},index = 6,converter = SexConverter.class)
    @ApiModelProperty(value = "用户性别(1:男;2:女)")
    @NotNull(message = "用户性别不能为空")
    private Integer gender;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","地址"},index = 7)
    @ApiModelProperty(value = "用户地址")
    private String address;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","类型"},index = 8,converter = UserTypeConverter.class)
    @ApiModelProperty(value = "用户类型")
    @NotNull(message = "无法识别的用户类型")
    private Integer type;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","是否锁定"},index = 9,converter = WhetherConverter.class)
    @ApiModelProperty(value = "是否锁定(0:否;1:是)")
    private Integer isLock;

    @ExcelIgnore
    @ApiModelProperty(value = "安全符")
    private String salt;

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

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户信息","版本"},index = 14)
    @ApiModelProperty(value = "版本号，乐观锁")
    @Version
    private Integer version;

    @ApiModelProperty(value = "token")
    @TableField(exist = false)
    @ExcelIgnore
    private String token;

    @ApiModelProperty(value = "短信验证码")
    @TableField(exist = false)
    @ExcelIgnore
    @NotEmpty(message = "请输入短信验证码")
    private String messageAuthCode;
}
