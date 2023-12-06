package com.example.new_sp.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName("feedback")
public class feedback {

    @TableField("FeedbackID")
    @JsonProperty("FeedbackID")
    private Integer FeedbackID;

    @TableField("AccountID")
    @JsonProperty("AccountID")
    private Integer AccountID;

    @TableField("content")
    @JsonProperty("content")
    private String content;
}
