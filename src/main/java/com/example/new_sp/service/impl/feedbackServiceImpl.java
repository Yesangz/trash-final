package com.example.new_sp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.new_sp.Exception.BusinessException;
import com.example.new_sp.Result.Code;
import com.example.new_sp.dao.feedbackDao;
import com.example.new_sp.domain.Car;
import com.example.new_sp.domain.feedback;
import com.example.new_sp.service.feedbackService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class feedbackServiceImpl extends ServiceImpl<feedbackDao, feedback> implements feedbackService {

    @Override
    public List<feedback> getFeedback() {
        return list();
    }

    @Override
    public boolean insertFeedback(feedback fb) {
        if (!Optional.ofNullable(fb.getContent()).isPresent()){
            return false;
        }
        return save(fb);
    }

    @Override
    public boolean dropFeedback(feedback fb) {
        if (!Optional.ofNullable(fb).isPresent()){
            throw new BusinessException(Code.DELETE_ERR,"参数为空");
        }
        LambdaQueryWrapper<feedback> lmq = new LambdaQueryWrapper<>();
        lmq.eq(feedback::getFeedbackID,fb.getFeedbackID());

        return remove(lmq);
    }

}
