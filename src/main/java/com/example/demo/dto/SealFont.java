package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @Description: 印章字体实体
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.dto
 * @Author: Suellen
 * @CreateDate: 2022/1/4 14:37
 */

@Builder
@Data
public class SealFont {

    private String text;
    private String family;
    private Integer size;
    private Boolean bold;
    private Double space;
    private Integer margin;
    public String getFamily() {
        return family == null ? "宋体" : family;
    }

    public boolean getBold() {
        return bold == null ? true : bold;
    }

    public SealFont append(String text) {
        this.text += text;
        return this;
    }
}
