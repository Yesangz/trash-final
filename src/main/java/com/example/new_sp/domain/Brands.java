package com.example.new_sp.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JacksonInject;
import lombok.Data;

@TableName("brands")
@Data
public class Brands {
    @TableField("BrandID")
    @JacksonInject("value")
    private Integer BrandID;

    @TableField("BrandName")
    @JacksonInject("label")
    private String BrandName;
}
