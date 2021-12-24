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
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 附件表
 * </p>
 *
 * @author zhk
 * @since 2021-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AttachmentInfo对象", description="附件表")
public class AttachmentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "附件主键")
    @TableId(value = "attachment_id", type = IdType.ASSIGN_UUID)
    private String attachmentId;

    @ApiModelProperty(value = "附件名称")
    private String attachmentName;

    @ApiModelProperty(value = "附件大小(kb)")
    private Integer attachmentSize;

    @ApiModelProperty(value = "附件类型")
    private String attachmentType;

    @ApiModelProperty(value = "附件地址")
    private String attachmentPath;

    @ApiModelProperty(value = "附件归属类型(1:图片;2:文件)")
    private String ascriptionType;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

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
