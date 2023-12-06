package com.example.new_sp.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data
public class Orders {
    @TableField("RecordID")
    @JsonProperty("RecordID")
    private Integer RecordID;

    @TableField("CustomerID")
    @JsonProperty("CustomerID")
    private Integer CustomerID;

    @TableField("CarID")
    @JsonProperty("CarID")
    private Integer CarID;

    @TableField("SalespersonID")
    @JsonProperty("SalespersonID")
    private Integer SalespersonID;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    @TableField("SalesDate")
    private Date SalesDate;

    @TableField("SalesPrice")
    @JsonProperty("SalesPrice")
    private double SalesPrice;

    @TableField("OrderState")
    @JsonProperty("OrderState")
    private String OrderState;
}
