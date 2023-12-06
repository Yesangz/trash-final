package com.example.new_sp.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName("car")
public class Car {
    @JsonProperty("CarID")
    @TableField("CarID")
    private Integer CarID;

    @JsonProperty("BrandID")
    @TableField("BrandID")
    private Integer BrandID;

    @JsonProperty("Brand")
    @TableField("Brand")
    private String Brand;

    @JsonProperty("Model")
    @TableField("Model")
    private String Model;

    @JsonProperty("Config")
    @TableField("Config")
    private String Config;


    @JsonProperty("Price")
    @TableField("Price")
    private Double Price;


    @JsonProperty("Quantity")
    @TableField("Quantity")
    private Integer Quantity;

    @JsonProperty("SalesState")
    @TableField("SalesState")
    private char SalesState;
}
