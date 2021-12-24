package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户主键")
    @TableId(value = "user_id", type = IdType.ASSIGN_UUID)
    private String userId;

    @ApiModelProperty(value = "用户名称")
    private String name;

    @ApiModelProperty(value = "用户账号")
    private String account;

    @ApiModelProperty(value = "账号密码")
    private String password;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "用户性别(1:男;2:女)")
    private Integer gender;

    @ApiModelProperty(value = "用户地址")
    private String address;

    @ApiModelProperty(value = "用户类型")
    private Integer type;

    @ApiModelProperty(value = "是否锁定(0:否;1:是)")
    private Integer isLock;

    @ApiModelProperty(value = "安全符")
    private String salt;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "最后修改密码时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastUpdatePasswordTime;

    @ApiModelProperty(value = "是否删除 0：否；1：是")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "版本号，乐观锁")
    @Version
    private Integer version;

    @ApiModelProperty(value = "token")
    @TableField(exist = false)
    private String token;

    @ApiModelProperty(value = "短信验证码")
    @TableField(exist = false)
    private String messageAuthCode;
}
