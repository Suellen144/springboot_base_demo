package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 客户维系规则信息表
 * </p>
 *
 * @author zhk
 * @since 2021-11-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MeCustomerSustainRuleInfo对象", description="客户维系规则信息表")
public class MeCustomerSustainRuleInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "客户维系规则主表ID")
    private Long ruleId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @ApiModelProperty(value = "规则名称")
    private String ruleName;

    @ApiModelProperty(value = "规则值")
    private String ruleValue;

    @ApiModelProperty(value = "规则编码")
    private String ruleCode;

    @ApiModelProperty(value = "前端组件类型：1：范围输入;2：单个输入;3：下拉选择;4：单选;5：多选;6：日期范围（年月日）;7：时间范围（年月日时分秒）;8：省市区;9：累计时长（时分秒）;10：单个输入（模糊）")
    private Integer frontType;

    @ApiModelProperty(value = "字段名称映射")
    private String fieldName;

    @ApiModelProperty(value = "创建人")
    private String createPerson;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    private String updatePerson;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除标志  0-正常  1-删除")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "业务定义扩展值")
    private String extension;


}
