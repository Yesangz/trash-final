package com.example.new_sp.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName("salespersongroup")
public class SalespersonGroup {
    @TableField("GroupId")
    @JsonProperty("GroupId")
    private Integer GroupId;

    @TableField("GroupName")
    @JsonProperty("GroupName")
    private String GroupName;
}
