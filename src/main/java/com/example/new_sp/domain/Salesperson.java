package com.example.new_sp.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Salesperson {
    @TableField("SalespersonID")
    @JsonProperty("SalespersonID")
    private Integer SalespersonID;

    @TableField("AccountID")
    @JsonProperty("AccountID")
    private Integer AccountID;

    @TableField("Name")
    @JsonProperty("Name")
    private String Name;

    @TableField("Phone")
    @JsonProperty("Phone")
    private String Phone;

    @TableField("Target")
    @JsonProperty("Target")
    private double Target;

    @TableField("Salesperson_group")
    @JsonProperty("Salesperson_group")
    private Integer Salesperson_group;
}
