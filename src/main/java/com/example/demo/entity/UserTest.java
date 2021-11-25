package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhk
 * @since 2021-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserTest对象", description="")
public class UserTest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "名字")
    private String name;


}
