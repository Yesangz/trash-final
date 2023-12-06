package com.example.new_sp.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName("supplier")
public class Supplier {
    @TableField("SupplierId")
    @JsonProperty("SupplierId")
    private Integer SupplierId;

    @TableField("AccountID")
    @JsonProperty("AccountID")
    private Integer AccountID;
}
