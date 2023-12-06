package com.example.new_sp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class account {
    @TableId(value = "ID")
    @JsonProperty("ID")
    private Integer ID;

    @TableField(value = "AccountName")
    @JsonProperty("AccountName")
    private String AccountName;

    @TableField(value = "Type")
    @JsonProperty("Type")
    private String Type;

    @TableField(value = "Authority")
    @JsonProperty("Authority")
    private Integer Authority;

    @TableField(value = "Password")
    @JsonProperty("Password")
    private String Password;

    public void addAuthority(int mask){
        this.Authority += mask;
    }
}
