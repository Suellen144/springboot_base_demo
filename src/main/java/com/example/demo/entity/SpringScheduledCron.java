package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 定时任务表
 * </p>
 *
 * @author zhk
 * @since 2022-01-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SpringScheduledCron对象", description="定时任务表")
public class SpringScheduledCron implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
      @TableId(value = "cron_id", type = IdType.AUTO)
    private Integer cronId;

    @ApiModelProperty(value = "定时任务完整类名")
    @NotBlank(message = "定时任务完整类名不能为空")
    private String cronKey;

    @ApiModelProperty(value = "cron表达式")
    @NotBlank(message = "cron表达式不能为空")
    private String cronExpression;

    @ApiModelProperty(value = "任务描述")
    private String taskExplain;

    @ApiModelProperty(value = "状态,1:正常;2:停用")
    @NotNull(message = "状态不能为空")
    private Integer status;


}
