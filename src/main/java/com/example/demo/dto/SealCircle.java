package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @Description: 印章圈实体
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.dto
 * @Author: Suellen
 * @CreateDate: 2022/1/4 14:36
 */

@Builder
@Data
public class SealCircle {
    private Integer line;
    private Integer width;
    private Integer height;
}
