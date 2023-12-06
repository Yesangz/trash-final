package com.example.new_sp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.new_sp.domain.feedback;

import java.util.List;

public interface feedbackService extends IService<feedback> {
    public List<feedback> getFeedback();
    public boolean insertFeedback(feedback fb);
    public boolean dropFeedback(feedback fb);
}
