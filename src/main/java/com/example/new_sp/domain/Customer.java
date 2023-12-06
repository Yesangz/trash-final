package com.example.new_sp.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Customer {
    @TableField(value = "CustomerID")
    @JsonProperty("CustomerID")
    private Integer CustomerID;

    @TableField(value = "AccountID")
    @JsonProperty("AccountID")
    private Integer AccountID;

    @JsonProperty("Name")
    private String Name;
    @JsonProperty("Phone")
    private String Phone;
    @JsonProperty("Email")
    private String Email;
    @JsonProperty("Intent")
    private String Intent;
    @JsonProperty("Address")
    private String Address;
}
